// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.intellij.sdk.action;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import icons.SdkIcons;
import org.jetbrains.annotations.NotNull;

/**
 * 演示如何在 plugin.xml 中静态地将操作组添加到菜单中，然后在其中创建菜单项
 * 运行时的组。有关 {@link DynamicActionGroup} 的声明，请参阅plugin.xml，并记下该组
 * 声明不包含操作。 {@link DynamicActionGroup} 基于 {@link ActionGroup}，因为菜单
 * 儿童是根据规则决定的，而不仅仅是位置限制。
 */
public class DynamicActionGroup extends ActionGroup {

  /**
   * Returns an array of menu actions for the group.
   *
   * @param e Event received when the associated group-id menu is chosen.
   * @return AnAction[] An instance of {@link AnAction}, in this case containing a single instance of the
   * {@link PopupDialogAction} class.
   */
  @Override
  public AnAction @NotNull [] getChildren(AnActionEvent e) {
    return new AnAction[]{
            new PopupDialogAction("Action Added at Runtime", "Dynamic Action Demo", SdkIcons.Sdk_default_icon)
    };
  }

}
