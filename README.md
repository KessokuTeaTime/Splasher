# Splasher!🧨

#### Customize Minecraft Splash Texts on Your Own!

## Contribute a Translation

Translate `assets/splasher/lang/en_us.json` into your favourite language and rename (etc. `zh_cn.json` ), then create a pull request to contribute!

## Add a Customization

Add a `.txt` file named the same as your target language (etc. `en_us.txt`) under `.minecraft/config/splasher/customizations` and include your own customized splash texts (Every single splash takes a single line, `§ Formatting Code` is under supported).
These files on existence will be automatically added to the splashes if Minecraft client is under the correct language.

Note that none of the customizations will not be included in the mod. Players should create them on their own.
*If you are a modpack developer, everything goes on the same way.*

## Apply One of Them

For now, splashes.txt will be automatically translated into the mod's built-in `client language.json` file(etc. client language `English(US)` calls a translation from `en_us.json`). 
If no available translation for the target language is provided, `en_us.json` will be loaded.

You can always ignore the language files and unstick to the translations by an option.

Splasher has an option for players to choose to load either a customized `splashes.txt` (instead of translations) or the original splashes (with translations), or, if you want, to load both of them.
There is also an option to disable all splashes conveniently.

### Options Graph in `.minecraft/config/splasher/splasher.properties`

#### (Only works when `enable_splash_texts` is `true`)

#### (`enable_festivals` decides whether show festival splash texts or not, such as `Merry X-mas!` on Christmas...)

| `splash_mode`            |           | VANILLA                                      | BOTH                                                                                    | CUSTOM                                   |
|--------------------------|-----------|----------------------------------------------|-----------------------------------------------------------------------------------------|------------------------------------------|
| `follow_client_language` | **false** | Original Minecraft Splashes (`splashes.txt`) | Minecraft Splashes + Custom Splashes (`splashes.txt` + `en_us.txt`)                     | Original Custom Splashes (`en_us.txt`)   |
| `follow_client_language` | **true**  | Translated Minecraft Splashes (`xx_xx.json`) | Translated Minecraft Splashes + Translated Custom Splashes (`xx_xx.json` + `xx_xx.txt`) | Translated Custom Splashes (`xx_xx.txt`) |


## Feature Schedule

#### Splasher is currently running on `Fabric` | `Minecraft 1.18.2`

- [X] Read Client Language Config
- [X] *- Customize Splash Text by String*
- [X] *- Auto Generated JSON Files*
- [X] Handle Mixin to Replace splashes.txt
- [X] *- Make Splashes in Mixin Translatable(etc. make Merry X-mas! splashes on every Christmas translatable)*
- [X] Automatically Load Customized Splash Texts (`.minecraft/config/splasher/customizations/xx_xx.txt`)
- [X] Advanced Options
- [X] *- Disable Splash Texts*
- [X] *- Optional Festival Splash Texts*
- [X] *- The Ability to Unfollow the Client Language*
- [X] *- 3 Different Splash Modes*
- [ ] ~~*- ModMenu GUI*~~
- [X] Better Code 2.0
- [ ] Even More Customizations on Splashes
- [ ] Splasher on More Minecraft Versions...
- [X] *- 1.18*
- [ ] *- 1.19*