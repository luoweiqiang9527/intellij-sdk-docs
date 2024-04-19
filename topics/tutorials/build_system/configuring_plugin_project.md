# Configuring Gradle IntelliJ Plugin

<!-- Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license. -->

<link-summary>配置基本的 Gradle IntelliJ 插件属性和任务。</link-summary>

本节将介绍 Gradle 插件属性以实现常用功能。
For more advanced options, see the full [Gradle IntelliJ Plugin](tools_gradle_intellij_plugin.md) reference.

## 保持最新状态

Gradle IntelliJ 插件和 [Gradle](https://gradle.org/install/) 构建系统不断开发，每个新版本都会带来重要的错误修复、新功能和改进，使开发更加高效。
强烈建议将 Gradle 和 Gradle IntelliJ 插件不断更新到最新版本。
较新版本的 IDE 版本在旧版本的 Gradle IntelliJ 插件中可能不完全受支持。

> Current Gradle IntelliJ Plugin version is %gradle-intellij-plugin-version%
{style="note"}

## 目标平台和依赖关系

<snippet id="whichPlatformVersion">

> Which versions should your plugin support? We've collected some insights based on download statistics in [Statistics: Product Versions in Use](https://plugins.jetbrains.com/docs/marketplace/product-versions-in-use-statistics.html).
>

</snippet>

默认情况下，Gradle 插件将针对 IntelliJ IDEA 社区版的最新 EAP 快照定义的 IntelliJ 平台构建插件项目。

如果本地计算机上没有指定 IntelliJ 平台的匹配版本，则 Gradle 插件会下载正确的版本和类型。
然后，IntelliJ IDEA 会索引构建以及任何关联的源代码和 JetBrains Java Runtime。

To build a plugin for more than one target platform version, see [](build_number_ranges.md#multipleIDEVersions) for important notes.

### IntelliJ 平台配置

Explicitly setting the [`intellij.version`](tools_gradle_intellij_plugin.md#intellij-extension-version) and [`intellij.type`](tools_gradle_intellij_plugin.md#intellij-extension-type) properties tells the Gradle plugin to use that configuration of the IntelliJ Platform to create the plugin project.

> See the [Developing for Multiple Products](dev_alternate_products.md) page for information about how to develop a plugin that is compatible with multiple IntelliJ-based IDEs.
>

All available platform versions can be browsed in the [](intellij_artifacts.md).

If the chosen platform version is not available in the repositories, or a local installation of the target IDE is the desired type and version of the IntelliJ Platform, use [`intellij.localPath`](tools_gradle_intellij_plugin.md#intellij-extension-localpath) to point to that installation.
If the `intellij.localPath` attribute is set, do not set the `intellij.version` and `intellij.type` attributes as this could result in undefined behavior.

### 插件依赖关系

IntelliJ Platform plugin projects may depend on either bundled or third-party plugins.
In that case, a project should build against a version of those plugins that match the IntelliJ Platform version used to build the plugin project.
The Gradle plugin will fetch any plugins in the list defined by [`intellij.plugins`](tools_gradle_intellij_plugin.md#intellij-extension-plugins).
See the Gradle plugin [IntelliJ Extension](tools_gradle_intellij_plugin.md#configuration-intellij-extension) for information about specifying the plugin and version.

Note that this attribute describes a dependency so that the Gradle plugin can fetch the required artifacts.
The runtime dependency must be added in the [Plugin Configuration](plugin_configuration_file.md) (<path>plugin.xml</path>) file as described in [Plugin Dependencies](plugin_dependencies.md#3-dependency-declaration-in-pluginxml).

## Run IDE Task

默认情况下，Gradle 插件将使用 IDE 开发实例的相同版本的 IntelliJ 平台，该版本与构建插件时使用的版本相同。
使用相应的 JetBrains Runtime 也是默认设置，因此对于此用例，不需要进一步配置。

### 针对基于 IntelliJ 平台的 IDE 的替代版本和类型运行

The IntelliJ Platform IDE used for the [Development Instance](ide_development_instance.md) can be different from that used to build the plugin project.
Setting the [`runIde.ideDir`](tools_gradle_intellij_plugin.md#tasks-runide-idedir) property will define an IDE to be used for the Development Instance.
This attribute is commonly used when running or debugging a plugin in an [alternate IntelliJ Platform-based IDE](intellij_platform.md#ides-based-on-the-intellij-platform).

### 针对 JetBrains 运行时的替代版本运行

Every version of the IntelliJ Platform has a corresponding version of the [JetBrains Runtime](ide_development_instance.md#using-a-jetbrains-runtime-for-the-development-instance).
A different version of the runtime can be used by specifying the [`runIde.jbrVersion`](tools_gradle_intellij_plugin.md#tasks-runide-jbrversion) attribute, describing a version of the JetBrains Runtime that should be used by the IDE Development Instance.
The Gradle plugin will fetch the specified JetBrains Runtime as needed.

## 修补插件配置文件

A plugin project's <path>plugin.xml</path> file has element values that are "patched" at build time from the attributes of the [`patchPluginXml`](tools_gradle_intellij_plugin.md#tasks-patchpluginxml) task.
As many as possible of the attributes in the Patching DSL will be substituted into the corresponding element values in a plugin project's <path>plugin.xml</path> file:
* If a `patchPluginXml` attribute default value is defined, the attribute value will be patched in <path>plugin.xml</path> _regardless of whether the `patchPluginXml` task appears in the Gradle build script_.
  * For example, the default values for the attributes [`patchPluginXml.sinceBuild`](tools_gradle_intellij_plugin.md#tasks-patchpluginxml-sincebuild) and [`patchPluginXml.untilBuild`](tools_gradle_intellij_plugin.md#tasks-patchpluginxml-untilbuild) are defined based on the declared (or default) value of [`intellij.version`](tools_gradle_intellij_plugin.md#intellij-extension-version).
    So by default `patchPluginXml.sinceBuild` and `patchPluginXml.untilBuild` are substituted into the [`<idea-version>`](plugin_configuration_file.md#idea-plugin__idea-version) element's `since-build` and `until-build` attributes in the <path>plugin.xml</path> file.
* If a [`patchPluginXml`](tools_gradle_intellij_plugin.md#tasks-patchpluginxml) task's attribute value is explicitly defined, the attribute value will be substituted in <path>plugin.xml</path>.
  * If both `patchPluginXml.sinceBuild` and `patchPluginXml.untilBuild` attributes are explicitly set, both are substituted in <path>plugin.xml</path>.
  * If one attribute is explicitly set (e.g. `patchPluginXml.sinceBuild`) and one is not (e.g. `patchPluginXml.untilBuild` has a default value), both attributes are patched at their respective (explicit and default) values.
* For **no substitution** of the `<idea-version>` element's `since-build` and `until-build` attributes, set [`intellij.updateSinceUntilBuild`](tools_gradle_intellij_plugin.md#intellij-extension-updatesinceuntilbuild) to `false`, and do not provide `patchPluginXml.sinceBuild` and `patchPluginXml.untilBuild` values.

The best practice to avoid confusion is to replace the elements in <path>plugin.xml</path> that will be patched by the Gradle plugin with a comment.
That way, the values for these parameters do not appear in two places in the source code.
The Gradle plugin will add the necessary elements as part of the patching process.
For those [`patchPluginXml`](tools_gradle_intellij_plugin.md#tasks-patchpluginxml) attributes that contain descriptions such as [`patchPluginXml.changeNotes`](tools_gradle_intellij_plugin.md#tasks-patchpluginxml-changenotes) and [`patchPluginXml.pluginDescription`](tools_gradle_intellij_plugin.md#tasks-patchpluginxml-plugindescription), a `CDATA` block is not necessary when using HTML elements.

> To maintain and generate an up-to-date changelog, try using [Gradle Changelog Plugin](https://github.com/JetBrains/gradle-changelog-plugin).
>

As discussed in [](creating_plugin_project.md#components-of-a-wizard-generated-gradle-intellij-platform-plugin), the Gradle properties `project.version`, `project.group`, and `rootProject.name` are all generated based on the input to the Wizard.
However, the [](tools_gradle_intellij_plugin.md) does not combine and substitute those Gradle properties for the default [`<id>`](plugin_configuration_file.md#idea-plugin__id) and [`<name>`](plugin_configuration_file.md#idea-plugin__name) elements in the <path>plugin.xml</path> file.

The best practice is to keep `project.version` current.
By default, if you modify `project.version` in Gradle build script, the Gradle plugin will automatically update the [`<version>`](plugin_configuration_file.md#idea-plugin__version) value in the <path>plugin.xml</path> file.
This practice keeps all version declarations synchronized.

## 验证插件

The Gradle plugin provides tasks that allow for running integrity and compatibility tests:
* [`verifyPluginConfiguration`](tools_gradle_intellij_plugin.md#tasks-verifypluginconfiguration) - validates the versions of SDK, target platform, APIs, etc., configured in a plugin project,
* [`verifyPlugin`](tools_gradle_intellij_plugin.md#tasks-verifyplugin) - validates completeness and contents of <path>plugin.xml</path> descriptors as well as plugin's archive structure,
* [`runPluginVerifier`](tools_gradle_intellij_plugin.md#tasks-runpluginverifier) - runs the [IntelliJ Plugin Verifier](https://github.com/JetBrains/intellij-plugin-verifier) tool to check the binary compatibility with specified IntelliJ IDE builds.

Plugin Verifier integration task allows for configuring the exact IDE versions that your plugin will be checked against.
See [](verifying_plugin_compatibility.md#plugin-verifier) for more information.

## 发布插件

Please review the [](publishing_plugin.md) page before using the [`publishPlugin`](tools_gradle_intellij_plugin.md#tasks-publishplugin) task.
That documentation explains different ways to use Gradle for plugin uploads without exposing account credentials.
