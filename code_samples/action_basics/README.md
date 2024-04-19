# Action Basics Sample Project [![JetBrains IntelliJ Platform SDK Docs](https://jb.gg/badges/docs.svg)][docs]
*Reference: [Action System in IntelliJ SDK Docs][docs:actions]*

## 快速入门

操作基础示例项目演示了在各种配置中注册操作的过程。
每个操作都是 ['AnAction'][sdk：AnAction] 抽象类的扩展，并带来了使用用户交互执行的事件（即，使用键盘或鼠标快捷键单击按钮）扩展 IDE 的可能性。

此插件以三种不同的方式注册 ['PopupDialogAction'][file：PopupDialogAction] 操作，该操作提供弹出对话框作为反馈：
- by assigning the keyboard (<kbd>Ctrl/Cmd</kbd>+<kbd>Alt</kbd>+<kbd>A</kbd>, <kbd>C</kbd>) and mouse shortcuts (<kbd>Ctrl/Cmd</kbd> + <kbd>Mouse Button 3</kbd> + <kbd>Double Click</kbd>),
- 通过直接将操作添加到`工具菜单`，并作为添加到“工具”菜单的新组的一部分，
- by adding an action to a new group in the `EditorPopupMenu`, which is the Editor's context menu.

插件的附加功能：
- [Using the `<override-text>`][docs:action-override] element in an [`<action>`][docs:plugin-configuration-file:actions:action] element is demonstrated in the `plugin.xml` declaration to add the `PopupDialogAction` action directly to the `ToolsMenu`.
- [Localization of action and group][docs:action-locale] `text` and `description` attributes using a [`<resource-bundle>`][docs:plugin-configuration-file:resource-bundle] is demonstrated in the declaration to add a new group to the `EditorPopupMenu`.

### Actions

| ID                                                 | Implementation                                            | Base Action Class |
|----------------------------------------------------|-----------------------------------------------------------|-------------------|
| `org.intellij.sdk.action.GroupPopDialogAction`     | [PopupDialogAction][file:PopupDialogAction]               | `AnAction`        |
| `org.intellij.sdk.action.PopupDialogAction`        | [PopupDialogAction][file:PopupDialogAction]               | `AnAction`        |
| `org.intellij.sdk.action.CustomGroupedAction`      | [PopupDialogAction][file:PopupDialogAction]               | `AnAction`        |
| `org.intellij.sdk.action.CustomDefaultActionGroup` | [CustomDefaultActionGroup][file:CustomDefaultActionGroup] | `ActionGroup`     |
| `org.intellij.sdk.action.DynamicActionGroup`       | [DynamicActionGroup][file:DynamicActionGroup]             | `ActionGroup`     |

*Reference: [Action System in IntelliJ SDK Docs][docs:actions]*


[docs]: https://plugins.jetbrains.com/docs/intellij/
[docs:actions]: https://plugins.jetbrains.com/docs/intellij/basic-action-system.html
[docs:action-override]: https://plugins.jetbrains.com/docs/intellij/basic-action-system.html#setting-the-override-text-element
[docs:action-locale]: https://plugins.jetbrains.com/docs/intellij/basic-action-system.html#localizing-actions-and-groups
[docs:plugin-configuration-file:actions:action]: https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html#idea-plugin__actions__action
[docs:plugin-configuration-file:resource-bundle]: https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html#idea-plugin__resource-bundle

[file:PopupDialogAction]: ./src/main/java/org/intellij/sdk/action/PopupDialogAction.java
[file:CustomDefaultActionGroup]: ./src/main/java/org/intellij/sdk/action/CustomDefaultActionGroup.java
[file:DynamicActionGroup]: ./src/main/java/org/intellij/sdk/action/DynamicActionGroup.java
