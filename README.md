### <p align=right>[`→` CurseForge](https://www.curseforge.com/minecraft/mc-mods/splasher)&ensp;[`→` Modrinth](https://modrinth.com/mod/splasher)</p>

![Banner](https://github.com/KessokuTeaTime/Splasher/blob/artwork/banner.png)

**Customize Minecraft's Splash Texts at Your Will!**

## Contributing a Translation

Splasher turns Minecraft `splashes.txt` into `.json` files, and supports multi languages.

You will find `en_us.json` under `assets/splasher/lang/` and feel free translate it into your favorite language.

After translation, rename it to `xx_xx.json` (e.g., `zh_cn.json`), and enjoy! You can also create a pull request to contribute.

## Adding a Customization

Splasher supports custom splash texts to append, or replace the original ones.

Custom splash texts are `.txt` files stored under `.minecraft/config/splasher/` and should be named the same as your target language (`en_us.txt` etc.).

In these files, every splash takes a single line. Legacy `§` formatting codes are supported.

## Configuring Splasher

Splasher is using **[Cloth Config API](https://modrinth.com/mod/cloth-config)** and is fully configurable, you can find the config file under `.minecraft/config/splasher.toml`.

Every option is well documented, and you can achieve different effects by changing the values, for example:

- Disabling all splashes
- Only enabling custom splashes
- Enabling both original and custom splashes, and not following the client language
- And more...

## Compatibility

**Splasher** supports Minecraft 26.2 on Fabric and NeoForge, requires Java 25 and Cloth Config, and offers optional Mod Menu integration on Fabric. It remains compatible with [Bounced](https://modrinth.com/mod/bounced), including accurate click detection while the title is animated.

## License

**Splasher** is licensed under the **[GNU General Public License v3.](LICENSE)**

## Screenshots

> ![Custom Splash Text](https://github.com/KessokuTeaTime/Splasher/blob/artwork/content/splash.png)
>
> Splasher displaying custom splash text `Splasher!`
