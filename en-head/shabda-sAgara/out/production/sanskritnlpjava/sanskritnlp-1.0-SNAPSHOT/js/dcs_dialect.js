function unicode_kyoto(input)
{
    var transformed = input;
    var i = 0;
    for(i=0;i<3;i++)
    {
        //old = transformed;
        transformed = transformed.replace(/ā/, 'A');
        transformed = transformed.replace(/ī/, 'I');
        transformed = transformed.replace(/ū/, 'U');
        transformed = transformed.replace(/ṛ/, 'R');
        transformed = transformed.replace(/ḷ/, 'L');
        transformed = transformed.replace(/ṅk/, 'nk');
        transformed = transformed.replace(/ṅg/, 'ng');
 
        transformed = transformed.replace(/ñc/, 'nc');
        transformed = transformed.replace(/ñj/, 'nj');
 
        transformed = transformed.replace(/ṭ/, 'T');
        transformed = transformed.replace(/ḍ/, 'D');
        transformed = transformed.replace(/ṇ/, 'N');
        transformed = transformed.replace(/ṣ/, 'sh');//retr.
        transformed = transformed.replace(/ś/, 'S');//pal
        transformed = transformed.replace(/ṃ/, 'M');
        transformed = transformed.replace(/ḥ/, 'H');
    }
    return transformed;
};