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
function togglePrismTheme() {
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

function toc() {
    $(function () {
        const navSelector = "#table_of_content";
        const $myNav = $(navSelector);
        Toc.init($myNav);
        $("#article").scrollspy({
            target: navSelector
        });
    });
}

const themeSwitch = document.querySelector('input');
themeSwitch.addEventListener('change', () => {
    document.body.classList.toggle('dark-theme');  // change web mode
    togglePrismTheme()  // switch prism theme
});

const renderer = new marked.Renderer();
renderer.code = function (code, lang, escaped) {
    code = this.options.highlight(code, lang);
    if (!lang) {
        return `<pre><code class="language-text">${code}</code></pre>`;
    }
    const langClass = "language-" + lang;
    return `<pre class="${langClass} rainbow-braces line-numbers">
                <code class="${langClass} match-braces">${code}</code>
            </pre>`;
}

marked.setOptions({
    renderer: renderer,
    highlight: function (code, lang) {
        try {
            console.log("Prism initialization")
            return Prism.highlight(code, Prism.languages[lang], lang);
        } catch {
            return code;
        }
    },
});

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

article_ajax = $.ajax({
    "type": 'get',
    "url": 'http://localhost:8080/downloadFile?id=1134629f-c718-4a7f-966e-c0483adf0d12',
    "dataType": "text",
    "success": function (data) {
        document.getElementById('article').innerHTML = marked(data);
        console.log("ajax:" + new Date())
        Prism.highlightAll();  // Rerun Prism syntax highlighting on the current page
        toc()
    }
});
$.when(article_ajax).done(function () {  // when article ajax done
    $('ul.navbar-nav .active').removeClass('active');
});

jQuery(function () {
    console.log("document is ready!")
    prev.addClass('disabled')  // init status
});
