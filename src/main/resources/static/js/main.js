function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("upward").style.visibility = "visible";
        document.getElementById("upward").style.opacity = "1";
    } else {
        document.getElementById("upward").style.visibility = "hidden";
        document.getElementById("upward").style.opacity = "0";
    }
}

// 点击按钮，返回顶部
function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

const themeSwitch = document.querySelector('input');
themeSwitch.addEventListener('change', () => {
    document.body.classList.toggle('dark-theme');  // change web mode
    togglePrismTheme()  // switch prism theme
});

jQuery(function () {
    // Handler for .ready() called.
});

