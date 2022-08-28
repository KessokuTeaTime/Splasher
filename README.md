# Splasher🧨

#### Customize Minecraft Splash Texts on Your Own!

## Contribute a Translation

Translate `assets/splasher/lang/en_us.json` into your favourite language and rename (etc. `zh_cn.json` ), then create a pull request to contribute!

## Add a Customization

`On Schedule` Add a txt file named the same as your target language (etc. `en_us.json`) under `.minecraft/config/splasher/customizations` and include your own customizations. 
One splash takes one line (Ofcourse you can use `§ Formatting Code`). 
These files on existence will be automatically added to the splashes if Minecraft client is under the correct language.

Note that none of the customizations will not be included in the mod. Players should operate them on their own.

## Apply One of Them

For now, splashes.txt will be automatically translated into your `client language.json` file(etc. client language `English(US)` calls a translation from `en_us.json`). 
If no available translation for the target language is provided, `en_us.json` will be loaded.

`Advancement` Splasher is considering an option for players to choose to load either a customized `splashes.txt` (instead of translations) or the original splashes (with translations), or, if you want, to load both of them.

`Advancement` There may be an option to disable all splashes conveniently.

## Feature Schedule

#### Splasher is currently running on `Fabric` | `Minecraft 1.18.2`

- [X] Read Client Language Config
- [X] *- Customize Splash Text by String*
- [X] *- Auto Generated JSON Files*
- [X] Handle Mixin to Replace splashes.txt
- [X] *- Make Splashes in Mixin Translatable(etc. make Merry X-mas! splashes on every Christmas translatable)*
- [ ] Automatically Load Customized Splash Texts
- [ ] Advanced Options
- [X] *- Disable Splash Texts*
- [X] *- Pure Vanilla Splash Texts*
- [ ] *- Unfollow the Client Language*
- [X] *- 3 Different Splash Modes*
- [ ] ~~*- ModMenu GUI*~~
- [ ] Splasher on More Platforms...