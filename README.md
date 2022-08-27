# Splasher🧨

#### Customize Minecraft Splash Texts on Your Own!

## Add a Customization

`May Change` Add a txt file named the same as your target language(etc. `en_us.json`) under `.minecraft/config/splasher/lang` and include your own translations(also customizations). These files on existence will automatically replace `splashes.txt` if under the correct language.

`Advancement` Splasher is considering an option for players to choose to load either a customized `splashes.txt`(instead of language-specified .json files) or the original `splashes.txt`(with language-spesified .json files), if so, the customized `splashes.txt` should be put under `.minecraft/config/splasher`.

## Apply One of Them

`May Change` For now, splashes.txt will be automatically replaced by your client language .json file(etc. client language `English(US)` calls a replacement from `en_us.json`).

If nothing is under the directory, Splasher will use the built-in `splashes.txt` from Minecraft.

## Feature Schedule

#### Splasher is currently running on `Fabric` | `Minecraft 1.18.2`

- [X] Read Client Language Config
- [X] *- Customize Splash Text by String*
- [ ] *- JSON File Structure I/O*
- [ ] Handle Mixin to Replace splashes.txt
- [ ] *- Make Splashes in Mixin Translatable(etc. make Merry X-mas! splashes on every Christmas translatable)*
- [ ] An Option to Disable Splasher Manually
- [ ] *- ModMenu GUI*
- [ ] Splasher on More Platforms...