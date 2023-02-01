[![GitHub Super-Linter](https://github.com/Waifu/OsancTools/workflows/Lint%20Code%20Base/badge.svg)](https://github.com/marketplace/actions/super-linter)

<img align="right" width="382" height="302" src="https://user-images.githubusercontent.com/58831335/216122008-08813e80-3aa5-4f2b-836d-fd361a72ad9b.png">

# RealmRPC

A simple Discord Rich Presence application for Realm of the Mad God that automatically updates information, such as the current dungeon and character stats, to be displayed on your discord profile.

## Installation

Download the [latest release](https://github.com/Waifu/RealmRPC/releases/latest) and run the executable.

Furthermore, you need to have [Npcap](https://npcap.com/#download) as well as Java installed.

## Build from Source

1. Open `build.gradle` with Intellij IDEA.
2. Add a configuration for the main function found in `com.github.waifu.Main`
3. To build, run the shadowjar task.

This project uses Launch4J to build the jar, but to run the artifact without wrapping into an executable, use the command:
```bash
java -jar <artifact_name>
```

## Contributing

Feel free to submit pull requests that handle specific issues or provide new and/or improved features. 
When in doubt, feel free to contact me.

## License

This repository is under the [MIT License](https://choosealicense.com/licenses/mit/). 
As such, feel free to modify the code to your liking. However, if your modifications intend
to further improve the rich presence, I highly encourage for these changes to go towards this project.
