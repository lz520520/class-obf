package me.n1ar4.clazz.obfuscator.config;

import com.beust.jcommander.Parameter;

@SuppressWarnings("all")
public class BaseCmd {
    @Parameter(names = {"-i", "--input"}, description = "input class file path")
    private String path;
    @Parameter(names = {"-c", "--config"}, description = "config yaml file")
    private String config;
    @Parameter(names = {"-g", "--generate"}, description = "generate config file")
    private boolean generate;
    @Parameter(names = {"-v", "--version"}, description = "version")
    private boolean version;

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
