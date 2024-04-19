<!-- Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license. -->

# Tool Windows

<link-summary>在 IDE 的子窗口中显示其他信息和控件。</link-summary>

<tldr>

**Product Help:** [Tool windows](https://www.jetbrains.com/help/idea/tool-windows.html)

**Platform UI Guidelines:** [Tool window](https://jetbrains.design/intellij/components/tool_window/)

</tldr>

_Tool windows_是用于显示信息的 IDE 的子窗口。
这些窗口通常沿着主窗口的外边缘有自己的工具栏（称为_tool窗口bars_），其中包含一个或多个_tool窗口buttons_，这些工具栏可激活显示在主 IDE 窗口的左侧、底部和右侧的面板。

每侧包含两个刀具窗口组（主刀窗组和辅助刀具窗组），并且每个刀具窗口一次只能处于活动状态。

每个工具窗口可以显示多个选项卡（或“内容”，因为它们在 API 中称为）。
例如，“<control>运行</control>”工具窗口显示每个活动运行配置的选项卡，而与“版本控制”相关的工具窗口显示一组固定的选项卡，具体取决于项目中使用的版本控制系统。

在插件中使用工具窗口有两种主要方案。
Using [declarative setup](#declarative-setup), 工具窗口按钮始终可见，用户可以随时激活它并与插件功能进行交互。
Alternatively, using [programmatic setup](#programmatic-setup), 创建工具窗口以显示特定操作的结果，然后在操作完成后关闭。

### 声明式设置

The tool window is registered in <path>[plugin.xml](plugin_configuration_file.md)</path> using the `com.intellij.toolWindow` extension point.
扩展点属性指定显示工具窗口按钮所需的所有数据：

* The `id` attribute (required) of the tool window which corresponds to the text displayed on the tool window button.
To provide a localized text, specify matching `toolwindow.stripe.[id]` message key (escape spaces with `_`) in the [resource bundle](plugin_configuration_file.md#idea-plugin__resource-bundle) (code insight supported in 2020.3 and later).

* The `icon` to display on the tool window button (13x13 pixels, grey and monochromatic; see [Tool window](https://jetbrains.design/intellij/components/tool_window/#07) in IntelliJ Platform UI Guidelines and [](icons.md))

* The `anchor`, meaning the side of the screen on which the tool window is displayed ("left" (default), "right" or "bottom")

* The `secondary` 属性，指定工具窗口是显示在主组还是辅助组中

* The `factoryClass` attribute (required), a class implementing [`ToolWindowFactory`](%gh-ic%/platform/platform-api/src/com/intellij/openapi/wm/ToolWindowFactory.kt).

当用户单击工具窗口按钮时，将调用工厂类的`createToolWindowContent()`方法，并初始化工具窗口的 UI。
此过程可确保未使用的工具窗口不会在启动时间或内存使用方面造成任何开销：如果用户不与工具窗互，则不会加载或执行插件代码。

#### 条件显示

If the tool window of a plugin should not be displayed for all projects:

<tabs>

<tab title="2023.3 and later">

Implement `ToolwindowFactory.isApplicableAsync(Project)`.

</tab>

<tab title="2021.1 and later">

Implement `ToolwindowFactory.isApplicable(Project)`.

</tab>

<tab title="2019.3 and earlier">

Specify the `conditionClass` attribute in <path>plugin.xml</path> with a class implementing [`Condition<Project>`](%gh-ic%/platform/util/src/com/intellij/openapi/util/Condition.java) (can be the same class as the `ToolWindowFactory` implementation).

</tab>

</tabs>

请注意，在加载项目时，仅评估一次条件。
要在用户使用项目时动态显示和隐藏工具窗口， use [programmatic setup](#programmatic-setup) for tool window registration.

### 编程设置

For toolwindows shown only after invoking specific actions, use [`ToolWindowManager.registerToolWindow(String,RegisterToolWindowTaskBuilder)`](%gh-ic%/platform/platform-api/src/com/intellij/openapi/wm/ToolWindowManager.kt).

Always use [`ToolWindowManager.invokeLater()`](%gh-ic%/platform/platform-api/src/com/intellij/openapi/wm/ToolWindowManager.kt) instead of "plain" `Application.invokeLater()` when scheduling EDT tasks related to tool windows (see [](general_threading_rules.md)).

## Contents (Tabs)

Displaying the contents of many tool windows requires access to [indexes](indexing_and_psi_stubs.md).
Because of that, tool windows are normally disabled while building indexes unless the `ToolWindowFactory` is marked [dumb aware](indexing_and_psi_stubs.md#DumbAwareAPI).

As mentioned previously, tool windows can contain multiple contents (tabs).
To manage the contents of a tool window, call [`ToolWindow.getContentManager()`](%gh-ic%/platform/ide-core/src/com/intellij/openapi/wm/ToolWindow.java).
To add a content (tab), first create it by calling [`ContentManager.getFactory().createContent()`](%gh-ic%/platform/ide-core/src/com/intellij/ui/content/ContentManager.java), and then to add it to the tool window using [`ContentManager.addContent()`](%gh-ic%/platform/ide-core/src/com/intellij/ui/content/ContentManager.java).
Use `Content.setDisposer()` to register associated `Disposable` (see [](disposers.md)).

See [`SimpleToolWindowPanel`](%gh-ic%/platform/platform-api/src/com/intellij/openapi/ui/SimpleToolWindowPanel.java) as a convenient base class, supporting [Toolbars](basic_action_system.md#building-ui-from-actions) and both vertical/horizontal layout.

### Closing Tabs

A plugin can control whether the user is allowed to close tabs either globally or on a per-content basis.
The former is done by passing the `canCloseContents` parameter to the `registerToolWindow()` function, or by specifying `canCloseContents="true"` in <path>plugin.xml</path>.
The default value is `false`; calling `setClosable(true)` on `ContentManager` content will be ignored unless `canCloseContents` is explicitly set.

If closing tabs is enabled in general, a plugin can disable closing of specific tabs by calling [`Content.setCloseable(false)`](%gh-ic%/platform/ide-core/src/com/intellij/ui/content/Content.java).

## Tool Window FAQ

### Accessing Tool Window

Use [`ToolWindowManager.getToolWindow()`](%gh-ic%/platform/platform-api/src/com/intellij/openapi/wm/ToolWindowManager.kt) specifying the `id` used for [registration](#declarative-setup).

### Tool Window Notification

[`ToolWindowManager.notifyByBalloon()`](%gh-ic%/platform/platform-api/src/com/intellij/openapi/wm/ToolWindowManager.kt) allows showing a notification for the given tool window.

### Events

Project-level topic [`ToolWindowManagerListener`](%gh-ic%/platform/platform-impl/src/com/intellij/openapi/wm/ex/ToolWindowManagerListener.java) allows listening to tool window registration/show events (see [](plugin_listeners.md)).

## Sample Plugin

To clarify how to develop plugins that create tool windows, consider the **toolWindow** sample plugin available in the [code samples](%gh-sdk-samples%/tool_window).

See [](code_samples.md) on how to set up and run the plugin.

This plugin creates the <control>Sample Calendar</control> tool window that displays the system date, time and time zone.
When opened, this tool window is similar to the following screen:

![Sample Calendar](sample_calendar.png){width="403"}
