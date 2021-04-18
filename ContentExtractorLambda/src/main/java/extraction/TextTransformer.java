package extraction;

import java.io.Reader;

class TextTransformer {

    String transformIntoPolishCharacters(String text) {
        text = text.replaceAll("£", "Ł");
        text = text.replaceAll("¥", "Ą");
        return text;
    }
}
