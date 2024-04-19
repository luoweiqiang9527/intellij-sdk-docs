<!-- Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license. -->

# 开发插件

<link-summary>使用 Gradle 和 Gradle IntelliJ 插件开发 IntelliJ 平台插件。</link-summary>

IntelliJ Platform plugins can be developed by using either [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/) or [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/download/) as your IDE.
强烈建议始终使用最新的可用版本，因为 _Plugin DevKit_ 的插件开发工具支持继续支持新功能。

在开始实际开发之前，请确保了解所有要求以实现最佳效果 [](plugin_user_experience.md).

<include from="intellij_platform.md" element-id="pluginAlternatives"/>

## Gradle IntelliJ Plugin

构建 IntelliJ 平台插件的推荐解决方案是 [](tools_gradle_intellij_plugin.md).

IntelliJ IDEA Ultimate 和 Community 版本提供了支持基于 Gradle 的插件开发所需的插件：_Gradle_ 和 _Plugin DevKit_。
要验证这些插件是否已安装并启用，请参阅有关 [Managing Plugins](https://www.jetbrains.com/help/idea/managing-plugins.html).

<include from="snippets.md" element-id="pluginDevKitAvailability"/>

Gradle IntelliJ Plugin manages the dependencies of a plugin project - both the base IDE and other [plugin dependencies](plugin_dependencies.md).
It provides tasks to run the IDE with your plugin and to package and [publish](publishing_plugin.md#publishing-plugin-with-gradle) your plugin to the [JetBrains Marketplace](https://plugins.jetbrains.com).
To make sure that a plugin is not affected by [API changes](api_changes_list.md), which may happen between major releases of the platform, you can quickly verify your plugin against other IDEs and releases.

There are two main ways of creating a new Gradle-based IntelliJ Platform plugin project:
- dedicated generator available in the [New Project Wizard](https://www.jetbrains.com/help/idea/new-project-wizard.html) - it creates a minimal plugin project with all the required files
- [](plugin_github_template.md) available on GitHub - in addition to the required project files, it includes configuration of the GitHub Actions CI workflows

This documentation section describes plugin structure generated with the <control>New Project</control> wizard, but the project generated with _IntelliJ Platform Plugin Template_ covers all the described files and directories.
See the [](plugin_github_template.md) section for more information about the advantages of this approach and instructions on how to use it.

> The old DevKit project model and workflow are still supported in existing projects and are recommended for [creating theme plugins](developing_themes.md).
> See how to [migrate a DevKit plugin to Gradle](migrating_plugin_devkit_to_gradle.md).
>

> A dedicated [SBT plugin](https://github.com/JetBrains/sbt-idea-plugin) is available for plugins implemented in Scala.
>

## Plugin Development Workflow

* [Creating a Gradle-based Plugin Project](creating_plugin_project.md)
* [Configuring the Gradle IntelliJ Plugin](configuring_plugin_project.md)
  * [Adding Kotlin Support](using_kotlin.md) (optional)
* [Publishing a Plugin](publishing_plugin.md)
