<!-- Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license. -->

# 插件类型

<link-summary>不同种类插件的概述和示例。</link-summary>

基于IntelliJ平台的产品可以通过添加插件进行修改和调整，以达到自定义目的。
All downloadable plugins are available from the [JetBrains Marketplace](https://plugins.jetbrains.com/).

最常见的插件类型包括：

* Custom language support
* Framework integration
* Tool integration
* 用户界面附加组件
* 主题

<include from="intellij_platform.md" element-id="pluginAlternatives"/>

## Custom Language Support

Custom language support provides basic functionality for working with a particular programming language, that includes:

* File type recognition
* Lexical analysis
* Syntax highlighting
* Formatting
* Code insight and code completion
* Inspections and quick fixes
* Intention actions

插件还可以增强现有的（捆绑的）自定义语言，例如，通过提供额外的检查、意图或任何其他功能。

Refer to the [Custom Language Support Tutorial](custom_language_support_tutorial.md) to learn more about the topic.

## 框架集成

框架集成包括改进的代码洞察功能（这是给定框架的典型特征），以及直接从 IDE 使用特定于框架的功能的选项。
有时，它还包括自定义语法或 DSL 的语言支持元素。

* Specific code insight
* Direct access to framework-specific functionality

Refer to the [IntelliJ-HCL](%gh-ij-plugins%/terraform) as an example of framework integration.
More reference plugins can be found on [JetBrains Marketplace](https://plugins.jetbrains.com/search?orderBy=update%20date&shouldHaveSource=true&tags=Framework).

## 工具集成

通过工具集成，可以直接从 IDE 操作第三方工具和组件，而无需切换上下文，这意味着：

* Implementation of additional actions
* Related UI components
* Access to external resources

Refer to the [Gerrit integration](https://plugins.jetbrains.com/plugin/7272) plugin as an example.

## 用户界面附加组件

此类别中的插件对 IDE 的标准用户界面进行各种更改。
一些新添加的组件是交互式的，并提供新功能，而其他组件仅限于视觉修改。
[Foldable ProjectView](https://plugins.jetbrains.com/plugin/17288-foldable-projectview) 插件可以作为一个例子。

## 主题

[Themes](themes_getting_started.md) 使设计人员能够自定义内置 IDE UI 元素的外观。
