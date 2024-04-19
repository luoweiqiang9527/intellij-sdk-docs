<!-- Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license. -->

# 用户界面组件

<link-summary>IntelliJ Platform 提供的 UI 组件简介。</link-summary>

<tldr>

**Platform UI Guidelines:** [Overview](https://jetbrains.design/intellij/)

</tldr>

The IntelliJ Platform includes a large number of custom [Swing](https://en.wikipedia.org/wiki/Swing_(Java)) components.
在插件中使用这些组件将确保插件的外观和工作与 IDE 其余部分的 UI 保持一致，并且与使用默认的 Swing 组件相比，通常可以减小代码大小.

<snippet id="inspectingExistingUI">

> Use [UI Inspector](internal_ui_inspector.md) to locate the underlying Swing component implementation or to inspect an existing UI at runtime.
>
{title="Inspecting existing UI"}

</snippet>

> UI forms like [](dialog_wrapper.md) or [](settings.md) should use [Kotlin UI DSL](kotlin_ui_dsl_version_2.md) (IntelliJ Platform 2021.3+).
>
> Using _UI Designer_ plugin with Kotlin is [not supported](https://youtrack.jetbrains.com/issue/KTIJ-791).
>
{style="note"}

Please refer to [Writing short and clear](https://jetbrains.design/intellij/text/writing_short/) in IntelliJ Platform UI Guidelines on writing UI-related texts.

See [UI Kit](https://jetbrains.design/intellij/resources/UI_kit/) when using [Figma](https://www.figma.com) to design UI.

The following components are particularly noteworthy:

* Menus and toolbars are built using [](basic_action_system.md)
* [](tool_windows.md)
* [](dialog_wrapper.md)
* [](popups.md)
* [](notifications.md)
* [](file_and_class_choosers.md)
* [](editor_components.md)
* [](lists_and_trees.md)
* [](status_bar_widgets.md)
* Tables (TableView) (TBD)
* Drag & Drop Helpers (TBD)
* [](misc_swing_components.md)

See also [](ui_faq.md).

<include from="snippets.md" element-id="missingContent"/>
