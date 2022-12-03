# [Splasher! 💥](https://modrinth.com/mod/splasher)

#### Customize Minecraft Splash Texts on Your Own!

![icon](/artwork/banner.png)

## Contribute a Translation

Translate `assets/splasher/lang/en_us.json` into your favorite language and rename (`zh_cn.json` etc.), then create a pull request to contribute!

## Add a Customization

Add a `.txt` file named the same as your target language (`en_us.txt` etc.) under `.minecraft/config/splasher/customizations` and include your own customized splash texts (Every single splash takes a single line, `§ Formatting Code` is under supported).
These files on existence will be automatically added to the splashes if Minecraft client is under the correct language.

Note that none of the customizations will not be included in the mod. Players should create them on their own.
*If you are a modpack developer, everything goes on the same way.*

## Apply One of Them

For now, splashes.txt will be automatically translated into the mod's built-in `client language.json` file(etc. client language `English(US)` calls a translation from `en_us.json`). 
If no available translation for the target language is provided, `en_us.json` will be loaded.

You can always ignore the language files and unstick to the translations by an option.

Splasher has an option for players to choose to load either a customized `splashes.txt` (instead of translations) or the original splashes (with translations), or, if you want, to load both of them.
There is also an option to disable all splashes conveniently.

### Options in `.minecraft/config/splasher/config.json5`

#### `enable_festivals` decides whether to show festival splash texts or not, such as `Merry X-mas!` on Christmas and so on.

#### `disable_debug_info` decides whether to log debug info or not, such as current splash mode and splash text.

#### Options Graph of `splash_mode` and `follow_client_language`

**(Only works when `enable_splash_texts` is set to `true`)**

| `splash_mode`<br /> \ <br />`follow_client_language` | VANILLA                                      | BOTH                                                                                    | CUSTOM                                   |
|------------------------------------------------------|----------------------------------------------|-----------------------------------------------------------------------------------------|------------------------------------------|
| **false**                                            | Original Minecraft Splashes (`splashes.txt`) | Minecraft Splashes + Custom Splashes (`splashes.txt` + `en_us.txt`)                     | Original Custom Splashes (`en_us.txt`)   |
| **true**                                             | Translated Minecraft Splashes (`xx_xx.json`) | Translated Minecraft Splashes + Translated Custom Splashes (`xx_xx.json` + `xx_xx.txt`) | Translated Custom Splashes (`xx_xx.txt`) |

#### Options Graph of `random_rate`

| NEVER                   | ON_RELOAD                                                                          | ON_CLICK                                                                                    | RELOAD_CLICK                                 |
|-------------------------|------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|----------------------------------------------|
| Load only after launch. | Reload after every reload actions (return from another gui, texture reload, etc.). | Reload only when the tiny yellow `⚡️`[^1] on the left side of Credit Info has been clicked. | Reload both after reload actions and clicks. |

[^1]: Click the flash icon to refresh splashes. <br /> ![Flash](/artwork/content/flash.png)

## Dependencies

Splasher! is depending on [Plume Config](https://github.com/KrLite/Plume-Config) [(Modrinth).](https://modrinth.com/mod/plumeconfig)

## Feature Schedule

#### Splasher is currently running on `Fabric / Quilt` `Minecraft 1.18.2`

- [X] Read Client Language Config
- [X] *- Customize Splash Text by String*
- [X] *- Auto Generated JSON Files*
- [X] Handle Mixin to Replace splashes.txt
- [X] *- Make Splashes in Mixin Translatable(make Merry X-mas! splashes on every Christmas translatable etc.)*
- [X] Automatically Load Customized Splash Texts (`.minecraft/config/splasher/customizations/xx_xx.txt`)
- [X] Advanced Options
- [X] *- Disable Splash Texts*
- [X] *- Optional Festival Splash Texts*
- [X] *- The Ability to Unfollow the Client Language*
- [X] *- Custom Splash Texts Random Rates*
- [X] *- 3 Different Splash Modes*
- [ ] ~~*- ModMenu GUI*~~
- [X] Better Code 2.0
- [ ] Even More Customizations on Splashes
- [ ] Splasher on More Minecraft Versions...
- [X] *- 1.18*
- [ ] *- 1.19*
