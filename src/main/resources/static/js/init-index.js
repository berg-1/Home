// 当网页向下滑动 20px 出现"返回顶部" 按钮
window.onscroll = function () {
    scrollFunction()
};

// horizontal gallery
let scrollWidth = 1000
const scroll = $("#scroll")[0];
const prev = $("#scroll_prev");
const next = $("#scroll_next");
const maxScrollLeft = scroll.scrollWidth - scroll.clientWidth;
scroll.onscroll = function () {
    if (scroll.scrollLeft === 0) {
        prev.addClass('disabled')
    } else if (scroll.scrollLeft === maxScrollLeft) {
        next.addClass('disabled')
    } else {
        prev.removeClass('disabled')
        next.removeClass('disabled')
    }
}

prev[0].onclick = function () {
    scroll.scrollBy({
        top: 0,
        left: -scrollWidth,
        behavior: 'smooth'
    });
};

next[0].onclick = function () {
    scroll.scrollBy({
        top: 0,
        left: scrollWidth,
        behavior: 'smooth'
    });
};


jQuery(function () {
    console.log("document is ready!")
    prev.addClass('disabled')  // init status
});
