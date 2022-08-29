package com.bytezone.diskbrowser2.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

// -----------------------------------------------------------------------------------//
class ViewMenu extends Menu //implements SaveState
// -----------------------------------------------------------------------------------//
{
  private static final boolean SHIFT = true;
  private static final boolean NO_SHIFT = !SHIFT;

  //  private final List<ShowLinesListener> showLinesListeners = new ArrayList<> ();
  //  private final LineDisplayStatus lineDisplayStatus = new LineDisplayStatus ();

  private final MenuItem fontMenuItem = new MenuItem ("Set Font...");
  private final MenuItem filterMenuItem = new MenuItem ("Set PDS Filter...");
  private final CheckMenuItem exclusiveFilterMenuItem = new CheckMenuItem ("Exclusive Filter");

  //  private final CheckMenuItem showLinesMenuItem;
  //  private final CheckMenuItem stripLinesMenuItem;
  //  private final CheckMenuItem truncateMenuItem;
  //  private final CheckMenuItem expandIncludeMenuItem;

  // ---------------------------------------------------------------------------------//
  public ViewMenu (String name)
  // ---------------------------------------------------------------------------------//
  {
    super (name);

    ObservableList<MenuItem> menuItems = getItems ();

    // Filter keys
    KeyCodeCombination cmdShiftF =
        new KeyCodeCombination (KeyCode.F, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN);
    KeyCodeCombination cmdF = new KeyCodeCombination (KeyCode.F, KeyCombination.SHORTCUT_DOWN);
    KeyCodeCombination cmdAltF =
        new KeyCodeCombination (KeyCode.F, KeyCombination.SHORTCUT_DOWN, KeyCombination.ALT_DOWN);

    fontMenuItem.setAccelerator (cmdAltF);
    filterMenuItem.setAccelerator (cmdF);
    exclusiveFilterMenuItem.setAccelerator (cmdShiftF);

    menuItems.add (fontMenuItem);
    menuItems.add (filterMenuItem);
    menuItems.add (exclusiveFilterMenuItem);
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
  private CheckMenuItem addCheckMenuItem (String name, KeyCode keyCode,
      EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    return addCheckMenuItem (name, keyCode, NO_SHIFT, action);
  }

  // ---------------------------------------------------------------------------------//
  private CheckMenuItem addCheckMenuItem (String name, KeyCode keyCode, boolean shift,
      EventHandler<ActionEvent> action)
  // ---------------------------------------------------------------------------------//
  {
    CheckMenuItem menuItem = new CheckMenuItem (name);
    getItems ().add (menuItem);

    if (keyCode != null)
      if (shift)
        menuItem.setAccelerator (new KeyCodeCombination (keyCode, KeyCombination.SHORTCUT_DOWN,
            KeyCombination.SHIFT_DOWN));
      else
        menuItem.setAccelerator (new KeyCodeCombination (keyCode, KeyCombination.SHORTCUT_DOWN));

    menuItem.setOnAction (action);

    return menuItem;
  }

  // ---------------------------------------------------------------------------------//
  //  private void notifyLinesListeners ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    LineDisplayStatus copy = new LineDisplayStatus (lineDisplayStatus);
  //
  //    for (ShowLinesListener listener : showLinesListeners)
  //      listener.showLinesSelected (copy);
  //  }

  // ---------------------------------------------------------------------------------//
  //  @Override
  //  public void restore (Preferences prefs)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    lineDisplayStatus.restore (prefs);
  //
  //    notifyLinesListeners ();
  //  }

  // ---------------------------------------------------------------------------------//
  //  @Override
  //  public void save (Preferences prefs)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    lineDisplayStatus.save (prefs);
  //  }

  // ---------------------------------------------------------------------------------//
  //  public void addShowLinesListener (ShowLinesListener listener)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    if (!showLinesListeners.contains (listener))
  //      showLinesListeners.add (listener);
  //  }
}
