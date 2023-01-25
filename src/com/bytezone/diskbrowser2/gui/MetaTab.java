package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class MetaTab extends DBTextTab implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  TreeFile treeFile;
  AppleFile appleFile;

  // ---------------------------------------------------------------------------------//
  public MetaTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    //    textFormatter = new TextFormatterHex ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    List<String> newLines = new ArrayList<> ();

    if (treeFile == null)
    {
      newLines.add ("No tree");
      return newLines;
    }

    if (treeFile.isLocalFile ())
    {
      newLines.add ("--> PC File");
      if (treeFile.isAppleFileSystem ())
      {
        newLines.add ("--> AppleFileSystem");
        for (String line : (((AppleFileSystem) treeFile.getAppleFile ()).toText ()).split ("\n"))
          newLines.add (line);
      }
    }
    else if (treeFile.isLocalDirectory ())
    {
      newLines.add ("--> PC Directory");
    }
    else if (treeFile.isAppleFileSystem ())
    {
      newLines.add ("--> AppleFileSystem");

      for (String line : (((AppleFileSystem) treeFile.getAppleFile ()).toText ()).split ("\n"))
        newLines.add (line);
    }
    else if (treeFile.isAppleFolder ())
    {
      newLines.add ("--> AppleFolder");
    }
    else if (treeFile.isAppleDataFile ())
    {
      newLines.add ("--> AppleDataFile");
      AppleFile appleFile = treeFile.getAppleFile ();
      newLines.add (appleFile.toString ());
    }

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.getAppleFile ();

    refresh ();
  }
}
