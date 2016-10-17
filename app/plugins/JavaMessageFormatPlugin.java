package plugins;

import play.PlayPlugin;
import play.i18n.Messages;

import java.text.MessageFormat;

public class JavaMessageFormatPlugin extends PlayPlugin {

    @Override
    public String getMessage(String locale, Object key, Object... args) {
        String value = null;
        if( key == null ) {
            return "";
        }
        if (Messages.locales.containsKey(locale)) {
            value = Messages.locales.get(locale).getProperty(key.toString());
        }
        if (value == null) {
            value = Messages.defaults.getProperty(key.toString());
        }
        if (value == null) {
            value = key.toString();
        }
        return MessageFormat.format(value, args);
    }
}