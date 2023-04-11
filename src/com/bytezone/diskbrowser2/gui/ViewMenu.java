package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.SaveState;
import com.bytezone.appbase.TabChangeListener;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

// -----------------------------------------------------------------------------------//
class ViewMenu extends Menu implements SaveState, FilterChangeListener, TabChangeListener
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_LINE_WRAP = "LineWrap";

  private final MenuItem fontMenuItem = new MenuItem ("Set Font...");
  private final MenuItem filterMenuItem = new MenuItem ("Set Text Filter...");
  private final CheckMenuItem exclusiveFilterMenuItem =
      new CheckMenuItem ("Exclusive Filter");
  private final CheckMenuItem lineWrapMenuItem = new CheckMenuItem ("Line Wrap");
  private DBTextTab currentTab;

  // ---------------------------------------------------------------------------------//
  public ViewMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    ObservableList<MenuItem> menuItems = getItems ();

    // Filter keys
    KeyCodeCombination cmdShiftF = new KeyCodeCombination (KeyCode.F,
        KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN);
    KeyCodeCombination cmdF =
        new KeyCodeCombination (KeyCode.F, KeyCombination.SHORTCUT_DOWN);
    KeyCodeCombination cmdAltF = new KeyCodeCombination (KeyCode.F,
        KeyCombination.SHORTCUT_DOWN, KeyCombination.ALT_DOWN);
    KeyCodeCombination cmdW =
        new KeyCodeCombination (KeyCode.W, KeyCombination.SHORTCUT_DOWN);

    fontMenuItem.setAccelerator (cmdAltF);
    filterMenuItem.setAccelerator (cmdF);
    exclusiveFilterMenuItem.setAccelerator (cmdShiftF);
    lineWrapMenuItem.setAccelerator (cmdW);

    menuItems.add (fontMenuItem);
    menuItems.add (filterMenuItem);
    menuItems.add (exclusiveFilterMenuItem);
    menuItems.add (lineWrapMenuItem);

    lineWrapMenuItem.setOnAction (e -> toggleLineWrap (e));
  }

  // ---------------------------------------------------------------------------------//
  void setFontAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    fontMenuItem.setOnAction (action);
  }

  // ---------------------------------------------------------------------------------//
  void setFilterAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    filterMenuItem.setOnAction (action);
  }

  // ---------------------------------------------------------------------------------//
  void setExclusiveFilterAction (EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    exclusiveFilterMenuItem.setOnAction (action);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    lineWrapMenuItem.setSelected (prefs.getInt (PREFS_LINE_WRAP, 0) == 1 ? true : false);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.putInt (PREFS_LINE_WRAP, lineWrapMenuItem.isSelected () ? 1 : 0);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFilter (FilterStatus filterStatus)
  // ---------------------------------------------------------------------------------//
  {
    exclusiveFilterMenuItem.setSelected (filterStatus.filterExclusion);
  }

  // ---------------------------------------------------------------------------------//
  void toggleLineWrap (ActionEvent e)
  // ---------------------------------------------------------------------------------//
  {
    if (currentTab != null)
      currentTab.toggleLineWrap ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void tabChanged (Tab fromTab, Tab toTab)
  // ---------------------------------------------------------------------------------//
  {
    if (toTab instanceof DBTextTab textTab)
    {
      lineWrapMenuItem.setDisable (false);
      currentTab = textTab;
      boolean lineWrap = currentTab.getLineWrap ();
      lineWrapMenuItem.setSelected (lineWrap);
    }
    else
      lineWrapMenuItem.setDisable (true);
  }
}
