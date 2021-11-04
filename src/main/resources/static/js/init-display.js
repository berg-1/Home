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

// Render marked.js
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

// set prime.js as the code render engine of marked.js
marked.setOptions({
    renderer: renderer,
    highlight: function (code, lang) {
        try {
            console.log("Prism initialization...")
            return Prism.highlight(code, Prism.languages[lang], lang);
        } catch {
            return code;
        }
    },
});

// get url argument
function getQueryVariable(variable) {
    const query = window.location.search.substring(1);
    const vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        const pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return false;
}

// use this as default id
let fid = "4f740429-7ace-4c6d-9877-ba5bdceb6e28"

if (getQueryVariable("id") !== false) {
    fid = getQueryVariable("id")
}
article_ajax = $.ajax({
    "type": 'get',
    "url": '/downloadFile?id=' + fid,
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
});

