### <p align=right>[`→` Modrinth](https://modrinth.com/mod/splasher)</p>

![Banner](https://github.com/KrLite/Splasher/blob/artwork/banner.png)

**Customize Minecraft's Splash Texts at Your Will!**

## Contributing a Translation

Splasher turns Minecraft `splashes.txt` into `.json` files, and supports multi languages.

You will find `en_us.json` under `assets/splasher/lang/` and feel free translate it into your favorite language.

After translation, rename it to `xx_xx.json` (etc. `zh_cn.json`), and enjoy! You can also create a pull request to contribute.

## Adding a Customization

Splasher supports custom splash texts to append, or replace the original ones.

Custom splash texts are `.txt` files stored under `.minecraft/config/splasher/` and should be named the same as your target language (`en_us.txt` etc.).

In these files, every single splash takes a single line, and `§ Formatting Code` is under supported, the same as the Minecraft `splashes.txt` under directory `.minecraft/assets/minecraft/texts/`.

## Configuring Splasher

Splasher is using **[Pierced](https://github.com/KrLite/Pierced)** and is fully configurable, you can find the config file under `.minecraft/config/splasher.toml`.

Every option is well documented, and you can achieve different effects by changing the values, for example:

- Disabling all splashes
- Only enabling custom splashes
- Enabling both original and custom splashes, and not following the client language
- And more...

## License

Splasher is licensed under [GNU General Public License](LICENSE).

## Screenshots

![Custom Splash Text](https://github.com/KrLite/Splasher/blob/artwork/content/splash.png)

> Splasher displaying custom splash text `Splasher!`
