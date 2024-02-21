package band.kessokuteatime.splasher.config;

import org.pkl.config.java.mapper.Named;
import org.pkl.config.java.mapper.NonNull;

import java.util.Objects;

public final class SplasherConfig {
  public final boolean splashTextsEnabled;

  public final boolean festivalsEnabled;

  public final boolean followsClientLanguage;

  public final @NonNull Debug debug;

  public final @NonNull Texts texts;

  public SplasherConfig(@Named("splashTextsEnabled") boolean splashTextsEnabled,
                        @Named("festivalsEnabled") boolean festivalsEnabled,
                        @Named("followsClientLanguage") boolean followsClientLanguage,
                        @Named("debug") @NonNull Debug debug, @Named("texts") @NonNull Texts texts) {
    this.splashTextsEnabled = splashTextsEnabled;
    this.festivalsEnabled = festivalsEnabled;
    this.followsClientLanguage = followsClientLanguage;
    this.debug = debug;
    this.texts = texts;
  }

  public SplasherConfig withSplashTextsEnabled(boolean splashTextsEnabled) {
    return new SplasherConfig(splashTextsEnabled, festivalsEnabled, followsClientLanguage, debug, texts);
  }

  public SplasherConfig withFestivalsEnabled(boolean festivalsEnabled) {
    return new SplasherConfig(splashTextsEnabled, festivalsEnabled, followsClientLanguage, debug, texts);
  }

  public SplasherConfig withFollowsClientLanguage(boolean followsClientLanguage) {
    return new SplasherConfig(splashTextsEnabled, festivalsEnabled, followsClientLanguage, debug, texts);
  }

  public SplasherConfig withDebug(@NonNull Debug debug) {
    return new SplasherConfig(splashTextsEnabled, festivalsEnabled, followsClientLanguage, debug, texts);
  }

  public SplasherConfig withTexts(@NonNull Texts texts) {
    return new SplasherConfig(splashTextsEnabled, festivalsEnabled, followsClientLanguage, debug, texts);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (this.getClass() != obj.getClass()) return false;
    SplasherConfig other = (SplasherConfig) obj;
    if (!Objects.equals(this.splashTextsEnabled, other.splashTextsEnabled)) return false;
    if (!Objects.equals(this.festivalsEnabled, other.festivalsEnabled)) return false;
    if (!Objects.equals(this.followsClientLanguage, other.followsClientLanguage)) return false;
    if (!Objects.equals(this.debug, other.debug)) return false;
    if (!Objects.equals(this.texts, other.texts)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + Objects.hashCode(this.splashTextsEnabled);
    result = 31 * result + Objects.hashCode(this.festivalsEnabled);
    result = 31 * result + Objects.hashCode(this.followsClientLanguage);
    result = 31 * result + Objects.hashCode(this.debug);
    result = 31 * result + Objects.hashCode(this.texts);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(300);
    builder.append(SplasherConfig.class.getSimpleName()).append(" {");
    appendProperty(builder, "splashTextsEnabled", this.splashTextsEnabled);
    appendProperty(builder, "festivalsEnabled", this.festivalsEnabled);
    appendProperty(builder, "followsClientLanguage", this.followsClientLanguage);
    appendProperty(builder, "debug", this.debug);
    appendProperty(builder, "texts", this.texts);
    builder.append("\n}");
    return builder.toString();
  }

  private static void appendProperty(StringBuilder builder, String name, Object value) {
    builder.append("\n  ").append(name).append(" = ");
    String[] lines = Objects.toString(value).split("\n");
    builder.append(lines[0]);
    for (int i = 1; i < lines.length; i++) {
      builder.append("\n  ").append(lines[i]);
    }
  }

  public static final class Debug {
    public final boolean debugInfoEnabled;

    public Debug(@Named("debugInfoEnabled") boolean debugInfoEnabled) {
      this.debugInfoEnabled = debugInfoEnabled;
    }

    public Debug withDebugInfoEnabled(boolean debugInfoEnabled) {
      return new Debug(debugInfoEnabled);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (this.getClass() != obj.getClass()) return false;
      Debug other = (Debug) obj;
      if (!Objects.equals(this.debugInfoEnabled, other.debugInfoEnabled)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + Objects.hashCode(this.debugInfoEnabled);
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(100);
      builder.append(Debug.class.getSimpleName()).append(" {");
      appendProperty(builder, "debugInfoEnabled", this.debugInfoEnabled);
      builder.append("\n}");
      return builder.toString();
    }
  }

  public static final class Texts {
    public final boolean colorful;

    public final boolean lefty;

    public final @NonNull RandomRate randomRate;

    public final @NonNull Source source;

    public Texts(@Named("colorful") boolean colorful, @Named("lefty") boolean lefty,
        @Named("randomRate") @NonNull RandomRate randomRate,
        @Named("source") @NonNull Source source) {
      this.colorful = colorful;
      this.lefty = lefty;
      this.randomRate = randomRate;
      this.source = source;
    }

    public Texts withColorful(boolean colorful) {
      return new Texts(colorful, lefty, randomRate, source);
    }

    public Texts withLefty(boolean lefty) {
      return new Texts(colorful, lefty, randomRate, source);
    }

    public Texts withRandomRate(@NonNull RandomRate randomRate) {
      return new Texts(colorful, lefty, randomRate, source);
    }

    public Texts withSource(@NonNull Source source) {
      return new Texts(colorful, lefty, randomRate, source);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (this.getClass() != obj.getClass()) return false;
      Texts other = (Texts) obj;
      if (!Objects.equals(this.colorful, other.colorful)) return false;
      if (!Objects.equals(this.lefty, other.lefty)) return false;
      if (!Objects.equals(this.randomRate, other.randomRate)) return false;
      if (!Objects.equals(this.source, other.source)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + Objects.hashCode(this.colorful);
      result = 31 * result + Objects.hashCode(this.lefty);
      result = 31 * result + Objects.hashCode(this.randomRate);
      result = 31 * result + Objects.hashCode(this.source);
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(250);
      builder.append(Texts.class.getSimpleName()).append(" {");
      appendProperty(builder, "colorful", this.colorful);
      appendProperty(builder, "lefty", this.lefty);
      appendProperty(builder, "randomRate", this.randomRate);
      appendProperty(builder, "source", this.source);
      builder.append("\n}");
      return builder.toString();
    }
  }

  public enum RandomRate {
    NEVER("never", false, false),

    ON_RELOAD("on reload", true, false),

    ON_CLICK("on click", false, true),

    ON_RELOAD_AND_CLICK("on reload and click", true, true),

    JENS("jens", false, false);

    private final String value;

    private final boolean onReload, onClick;

    RandomRate(String value, boolean onReload, boolean onClick) {
      this.value = value;
      this.onReload = onReload;
      this.onClick = onClick;
    }

    public boolean onReload() {
      return onReload;
    }

    public boolean onClick() {
      return onClick;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }

  public enum Source {
    DISABLED("disabled", false, false),

    VANILLA("vanilla", true, false),

    CUSTOM("custom", false, true),

    VANILLA_AND_CUSTOM("vanilla and custom", true, true);

    private final String value;

    private final boolean vanilla, custom;

    Source(String value, boolean vanilla, boolean custom) {
      this.value = value;
      this.vanilla = vanilla;
      this.custom = custom;
    }

    public boolean vanilla() {
      return vanilla;
    }

    public boolean custom() {
      return custom;
    }

    @Override
    public String toString() {
      return this.value;
    }
  }
}
