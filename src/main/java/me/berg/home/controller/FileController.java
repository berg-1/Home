package me.berg.home.controller;

import lombok.extern.slf4j.Slf4j;
import me.berg.home.exception.LargeFileException;
import me.berg.home.model.FileData;
import me.berg.home.model.MyFile;
import me.berg.home.service.FileDataService;
import me.berg.home.service.MyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Controller
public class FileController {

    @Autowired
    FileDataService dataService;

    @Autowired
    MyFileService myFileService;

    /**
     * 最大上传限制
     */
    private static final Long MAX_UPLOAD_SIZE = 2 * 10 * 1024 * 1024L;

    /**
     * 上传一个 File 实体到数据库
     *
     * @param file               文件数据 -> MultipartFile
     * @param redirectAttributes 重定向传值需要
     * @return main_student.html
     */
    @PostMapping("/uploadFile")
    public String singleFileUpload(@RequestParam(value = "file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            // 取得文件并以Bytes方式保存
            byte[] bytes = file.getBytes();
            if (bytes.length > MAX_UPLOAD_SIZE) {
                throw new LargeFileException(MAX_UPLOAD_SIZE);
            }
            String uuid = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename();
            String type = file.getContentType();
            System.out.println(type);
            dataService.save(new FileData(uuid, bytes));
            myFileService.save(new MyFile(uuid, fileName, type, (short) 1000));
        } catch (LargeFileException largeFileException) {
            redirectAttributes.addFlashAttribute("message", String.format(
                    "文件过大,上传失败!请将文件控制在%dMB内!",
                    largeFileException.getMaxSize() / 1048576
            ));
        } catch (Exception s) {
            redirectAttributes.addFlashAttribute("message", "上传失败!");
            s.printStackTrace();
        }
        return "upload";
    }

    @RequestMapping("/downloadFile")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("id") String id) {
        log.debug("下载文件:id={}", id);
        FileData fileData = dataService.getById(id);
        MyFile myFile = myFileService.getById(id);
        String filename = myFile.getFilename();
        byte[] bytes = fileData.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", myFile.getTypename() + ";charset=utf-8");
        headers.setContentDispositionFormData("attachment",
                new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

}
