package me.berg.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.berg.home.exception.LargeFileException;
import me.berg.home.model.FileData;
import me.berg.home.model.MyFile;
import me.berg.home.service.FileDataService;
import me.berg.home.service.MyFileService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {

    /**
     * 最大上传限制
     */
    private static final Long MAX_UPLOAD_SIZE = 2 * 10 * 1024 * 1024L;
    private final FileDataService dataService;
    private final MyFileService myFileService;

    /**
     * 上传一个 File 实体到数据库
     *
     * @param file               文件数据 -> MultipartFile
     * @param redirectAttributes 重定向传值需要
     * @return main_student.html
     */
    @PostMapping("/uploadFile")
    public String singleFileUpload(@RequestParam(value = "file") MultipartFile file,
                                   String description,
                                   @RequestParam(defaultValue = "9999") short project,
                                   RedirectAttributes redirectAttributes) {
        try {
            // 取得文件并以Bytes方式保存
            byte[] bytes = file.getBytes();
            if (bytes.length == 0) return "upload";
            if (bytes.length > MAX_UPLOAD_SIZE) {
                throw new LargeFileException(MAX_UPLOAD_SIZE);
            }
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            // 根据文件名生成UUID
            String uuid = UUID.nameUUIDFromBytes(fileName.getBytes(StandardCharsets.UTF_8)).toString();
            String type = file.getContentType();
            if (Objects.equals(type, "application/octet-stream")) {
                log.info("got FILE.");
                bytes = replaceImages(bytes);
            }
            if (description == null) {
                description = new String(bytes).replaceAll("\\r*\\n* *#*", "").substring(0, 70) + "...";
            }
            dataService.save(new FileData(uuid, bytes));
            myFileService.save(new MyFile(uuid, fileName, type, project, null, description));
        } catch (LargeFileException largeFileException) {
            redirectAttributes.addFlashAttribute("message", String.format(
                    "文件过大,上传失败!请将文件控制在%dMB内!",
                    largeFileException.getMaxSize() / 1048576
            ));
        } catch (DuplicateKeyException e) {
            redirectAttributes.addFlashAttribute("重复文件上传!");
            log.info("Duplicate upload!:{}", file.getOriginalFilename());
            return "redirect:/upload";
        } catch (Exception s) {
            redirectAttributes.addFlashAttribute("message", "上传失败!");
            s.printStackTrace();
        }
        return "redirect:/upload";
    }

    /**
     * 下载文件
     *
     * @param id 文件ID
     * @return 想要下载的文件
     */
    @RequestMapping("/downloadFile")
//    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
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


    /**
     * 将所有图片替换为UUID img\1.1.1.OS作为接口示意图.jpg -> img\f5c593d3-13f2-3da8-9d6a-4435270d27d2
     *
     * @param bytes MyFile bytes
     * @return bytes
     */
    public byte[] replaceImages(byte[] bytes) {
        String imagePattern = "(img\\\\)([\\s\\S]+?\\.png|[\\S\\s]+?\\.jpg)";
        String fileString = new String(bytes);
        Pattern imageCompiler = Pattern.compile(imagePattern);
        Matcher imageMatcher = imageCompiler.matcher(fileString);
        String output = new String(bytes);
        while (imageMatcher.find()) {
            int start = imageMatcher.start() + 4;
            int end = imageMatcher.end();
            String substring = fileString.substring(start, end);
            String uuid = UUID.nameUUIDFromBytes(substring.getBytes(StandardCharsets.UTF_8)).toString();
            output = output.replace(substring, uuid);
        }
        return output.getBytes(StandardCharsets.UTF_8);
    }
}
