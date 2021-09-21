// 当网页向下滑动 20px 出现"返回顶部" 按钮
window.onscroll = function () {
    scrollFunction()
    catalogTrack()
};

const catalogTrack = () => {
    let $currentHeading = $('h1');
    for (let heading of $('h1,h2')) {
        const $heading = $(heading);
        if ($heading.offset().top - $(document).scrollTop() > 20) {
            break;
        }
        $currentHeading = $heading;
    }
    const anchorName = $currentHeading.attr('id');
    const $catalog = $(`a[href="#${anchorName}"]`);
    if (!$catalog.hasClass('active')) {
        $('ul.navbar-nav .active').removeClass('active');
        $catalog.addClass('active');
    }
};


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

// toggle between dark theme or light theme
function toggle_prism_theme() {
    const light = document.getElementById("light-theme"),
        dark = document.getElementById("dark-theme");
    if (light.disabled === true) {
        light.disabled = undefined;
        // wait theme perfectly changed :)
        setTimeout(function () {
            dark.disabled = "disabled";
        }, 300);
    } else {
        dark.disabled = undefined;
        setTimeout(function () {
            light.disabled = "disabled";
        }, 300);
    }
}

const themeSwitch = document.querySelector('input');
themeSwitch.addEventListener('change', () => {
    document.body.classList.toggle('dark-theme');  // change web mode
    toggle_prism_theme()  // switch prism theme
    // change background
});
