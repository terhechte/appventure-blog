var system = require('system');
var fs = require('fs');

var code = null;

if (system.args.length !== 3) {
    console.log('Usage: render.js template outpath/outfile ');
    phantom.exit();
} else {
    //  this blocks even in phantom.exit case
    // yeah...
    code = system.stdin.read(1024 * 1024);
}


//console.log(code);
var template = system.args[1];
var outfile = system.args[2];

// change the working directory to the current library path
fs.changeWorkingDirectory(phantom.libraryPath);

var templateContent = fs.read(template + ".html");
phantom.injectJs('shared/handlebars.js');
var handlebar = Handlebars.compile(templateContent);

var renderedTemplate = handlebar({code: code});

var temporary_outfile = "tmp_render_outfile.html";

fs.write(temporary_outfile, renderedTemplate, "w");

var page = require('webpage').create();
page.onLoadFinished = function(status) {
    setTimeout(function(){
        
        var size = page.evaluate(function() {
            // calculate the min dimensions
            var w = 0;
            var h = 0;
            $("#uiui>*>*").each(function (i, l) {
                var l = $(l);
                var nw = l.outerWidth() + l.position().left;
                if (nw > w)w = nw;
                var nh = l.outerHeight() + l.position().top;
                if (nh > h)h = nh;
            });
            return {w: w, h: h};
        });

        // The page has to be reloaded after the viewPort size changed.
        // so we're creating a new page
        var page2 = require('webpage').create();

        // include a certain padding
        page2.viewportSize = {
            width: size.w + 50,
            height: size.h + 50
        };

        page2.open(temporary_outfile, function() {
            page2.render(outfile, {quality: '100'});

            // delete the remnants
            fs.remove(temporary_outfile);

            phantom.exit();
        });

    }, 100);
};

page.open(temporary_outfile, function() {
});


