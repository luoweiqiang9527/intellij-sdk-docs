<!-- Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license. -->

# Notifications

<link-summary>通过显示需要确认的模式消息框，在不中断其工作流的情况下通知用户有关错误、操作状态或其他事件的信息。</link-summary>

<tldr>

**Platform UI Guidelines:** [Notifications](https://jetbrains.design/intellij/controls/notifications/), [Banner](https://jetbrains.design/intellij/controls/banner/), [Got It tooltip](https://jetbrains.design/intellij/controls/got_it_tooltip/), [Balloon](https://jetbrains.design/intellij/controls/balloon/)

</tldr>

主要的设计原则之一是避免使用模态消息框来通知用户有关错误和其他可能需要用户注意的情况。
作为替代，IntelliJ 平台提供了多个非模式通知 UI 选项。

### 对话框

When working in a dialog, instead of checking the validity of the input when the <control>OK</control> button is pressed and notifying the user about invalid data with a modal dialog, the recommended approach is to use
[`DialogWrapper.doValidate()`](%gh-ic%/platform/platform-api/src/com/intellij/openapi/ui/DialogWrapper.java),
described in the [Dialogs](dialog_wrapper.md#input-validation) section.

### 编辑提示

For actions invoked from the editor (such as refactorings, navigation actions, and different code insight features), the best way to notify the user about the inability to perform an action is to use the [`HintManager`](%gh-ic%/platform/platform-api/src/com/intellij/codeInsight/hint/HintManager.java) class.
Its method `showErrorHint()` displays a floating popup above the editor which is automatically hidden when the user starts performing another action in the editor.
Other [`HintManager`](%gh-ic%/platform/platform-api/src/com/intellij/codeInsight/hint/HintManager.java) methods can be used for displaying other kinds of non-modal notification hints over an editor.

### 编辑横幅

For UI reference, see [Banner](https://jetbrains.design/intellij/controls/banner/) in the IntelliJ Platform UI Guidelines.

显示在文件编辑器顶部的通知是要求用户执行重要操作的好方法，否则如果忽略这些操作（例如，缺少 SDK、需要用户输入的设置/项目配置），则会影响他们的体验。

Register an implementation of [`EditorNotifications.Provider`](%gh-ic%/platform/platform-api/src/com/intellij/ui/EditorNotifications.java) using `com.intellij.editorNotificationProvider` extension point.
If access to indexes is not required, it can be marked [dumb aware](indexing_and_psi_stubs.md#DumbAwareAPI).

A commonly used UI implementation is [`EditorNotificationPanel`](%gh-ic%/platform/platform-api/src/com/intellij/ui/EditorNotificationPanel.java).

### “知道了”通知

Use to highlight important new/changed features via [`GotItTooltip`](%gh-ic%/platform/platform-impl/src/com/intellij/ui/GotItTooltip.kt).
See [Got It tooltip](https://jetbrains.design/intellij/controls/got_it_tooltip/) in IntelliJ Platform UI Guidelines for an overview.

### 顶级通知（气球）
{id="balloons"}

显示非模式通知的最通用方法是使用 [`Notifications`](%gh-ic%/platform/ide-core/src/com/intellij/notification/Notifications.java) class.

它有两个主要优点：

* The user can control the way each notification type is displayed under <ui-path>Settings | Appearance & Behavior | Notifications</ui-path>
* All displayed notifications are gathered in the <control>Event Log</control> tool window and can be reviewed later

For UI reference, see [Balloon](https://jetbrains.design/intellij/controls/balloon/) in the IntelliJ Platform UI Guidelines.

> See [](tool_windows.md#tool-window-notification) for showing balloons for a specific tool window.

The specific method used to display a notification is [`Notifications.Bus.notify()`](%gh-ic%/platform/ide-core/src/com/intellij/notification/Notifications.java).
If the current Project is known, please use overload with `Project` parameter, so the notification is shown in its associated frame.

The text of the notification can include HTML tags for presentation purposes.
Use `Notification.addAction(AnAction)` to add links below the content, use [`NotificationAction`](%gh-ic%/platform/ide-core/src/com/intellij/notification/NotificationAction.java) for convenience.

The `groupId` parameter of the [`Notification`](%gh-ic%/platform/ide-core/src/com/intellij/notification/Notification.java) constructor specifies a notification type.
The user can choose the display type corresponding to each notification type under <ui-path>Settings | Appearance & Behavior | Notifications</ui-path>.

To specify the preferred display type, you need to use [`NotificationGroup`](%gh-ic%/platform/ide-core/src/com/intellij/notification/NotificationGroup.kt) to create notifications.

Please see the following steps for setup, depending on the target platform version.

<tabs>

<tab title="2020.3 and later">

`NotificationGroup` is registered in <path>[plugin.xml](plugin_configuration_file.md)</path> using `com.intellij.notificationGroup` extension point.
Use `key` to provide a localized group display name.

```xml

<extensions defaultExtensionNs="com.intellij">
  <notificationGroup id="Custom Notification Group"
                     displayType="BALLOON"
                     key="notification.group.name"/>
</extensions>
```

Registered instances can then be obtained via their `id`.

> Code insight is available for parameters expecting a notification group `id`.
>

<br/>

```java
public class MyNotifier {

  public static void notifyError(Project project, String content) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup("Custom Notification Group")
        .createNotification(content, NotificationType.ERROR)
        .notify(project);
  }

}
```

</tab>

<tab title="Pre-2020.3">

`NotificationGroup` is registered in code.

```java
public class MyNotifier {

  private static final NotificationGroup NOTIFICATION_GROUP =
      new NotificationGroup("Custom Notification Group",
              NotificationDisplayType.BALLOON, true);

  public static void notifyError(Project project, String content) {
    NOTIFICATION_GROUP.createNotification(content, NotificationType.ERROR)
        .notify(project);
  }

}
```

</tab>

</tabs>
