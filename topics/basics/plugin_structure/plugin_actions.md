<!-- Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license. -->

# Actions

<link-summary>介绍允许调用插件功能的操作。</link-summary>

IntelliJ 平台提供了 _actions_ 的概念。
操作是从 [`AnAction`](%gh-ic%/platform/editor-ui-api/src/com/intellij/openapi/actionSystem/AnAction.java), 当选择其菜单项或工具栏按钮时，将调用其“actionPerformed（）”方法。

操作是用户调用插件功能的最常见方式。
可以从菜单或工具栏调用操作，使用键盘快捷键或 <ui-path>Help | Find Action...</ui-path> lookup.

操作被组织成组，而这些组又可以包含其他组。
一组操作可以形成工具栏或菜单。
组的子组可以形成菜单的子菜单。

用户可以通过以下方式自定义所有已注册的操作[Menus and Toolbars](https://www.jetbrains.com/help/idea/customize-actions-menus-and-toolbars.html) 设置。

Please see [Action System](basic_action_system.md) on how to create and register actions in the IDE.
