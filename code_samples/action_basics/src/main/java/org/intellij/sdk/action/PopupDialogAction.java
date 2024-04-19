// Copyright 2000-2023 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.intellij.sdk.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 操作类,用于演示如何与 IntelliJ 平台进行交互。
 * 此类执行的唯一操作是向用户提供弹出对话框作为反馈。
 * 通常，此类由 IntelliJ 平台框架基于声明进行实例化
 * 在 plugin.xml 文件中。但是，在运行时添加此类时，此类由操作组实例化。
 */
public class PopupDialogAction extends AnAction {

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  /**
   * IntelliJ 平台框架使用此默认构造函数基于 plugin.xml 实例化此类
   * 声明。仅在 {@link PopupDialogAction} 类中需要，因为第二个构造函数被覆盖。
   */
  public PopupDialogAction() {
    super();
  }

  /**
   * 此构造函数用于支持动态添加的菜单操作。
   * 它设置要为菜单项显示的文本、描述。
   * 否则，IntelliJ 平台将使用默认的 AnAction 构造函数。
   *
   * @param文本 要显示为菜单项的文本。
   * @param描述菜单项的描述。
   * @param图标 要与菜单项一起使用的图标。
   */
  public PopupDialogAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
    super(text, description, icon);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    // 使用事件，创建并显示对话框
    Project currentProject = event.getProject();
    StringBuilder message =
        new StringBuilder(event.getPresentation().getText() + " Selected!");
    // If an element is selected in the editor, add info about it.
    Navigatable selectedElement = event.getData(CommonDataKeys.NAVIGATABLE);
    if (selectedElement != null) {
      message.append("\nSelected Element: ").append(selectedElement);
    }
    String title = event.getPresentation().getDescription();
    Messages.showMessageDialog(
        currentProject,
        message.toString(),
        title,
        Messages.getInformationIcon());
  }

  @Override
  public void update(AnActionEvent e) {
    // 根据项目是否打开设置可用性
    Project project = e.getProject();
    e.getPresentation().setEnabledAndVisible(project != null);
  }

}
