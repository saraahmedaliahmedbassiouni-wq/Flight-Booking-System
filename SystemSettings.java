public class SystemSettings {
    private String theme;
    private boolean autoSave;
    private String language;

    public SystemSettings(String theme, boolean autoSave, String language) {
        this.theme = theme;
        this.autoSave = autoSave;
        this.language = language;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Theme: " + theme + ", AutoSave: " + autoSave + ", Language: " + language;
    }
}
