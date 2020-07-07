CKEDITOR.plugins.add('iplugin',
{
    init: function (editor) {
        var pluginName = 'iplugin';
        editor.ui.addButton('Iplugin',
            {
                label: 'ادراج صورة',
                command: 'OpenWindow',
                icon: CKEDITOR.plugins.getPath('iplugin') + 'image.png'
            });
        var cmd = editor.addCommand('OpenWindow', { exec: showMyDialog });
    }
});
function showMyDialog(e) {
    window.open('/medias/', 'MyWindow', 'width=710,height=600,scrollbars=no,scrolling=no,location=no,toolbar=no');
}
function InsertHTML(file_path)
        {
            // Get the editor instance that we want to interact with.
            var oEditor = CKEDITOR.instances.article_content;
            var value = file_path;

            // Check the active editing mode.
            if ( oEditor.mode == 'wysiwyg' )
            {
                // Insert the desired HTML.
                oEditor.insertHtml( '<img width="100%" src="' + value + '" />' );
            }
            else
                alert( 'You must be on WYSIWYG mode!' );
        }
