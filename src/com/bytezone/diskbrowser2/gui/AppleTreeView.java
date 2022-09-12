package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.bytezone.appbase.FontChangeListener;
import com.bytezone.appbase.SaveState;
import com.bytezone.filesystem.FileSystemFactory;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Callback;

// ---------------------------------------------------------------------------------//
public class AppleTreeView extends TreeView<TreeFile> implements SaveState, FontChangeListener
// ---------------------------------------------------------------------------------//
{
  public static FileSystemFactory factory = new FileSystemFactory ();
  private static final String PREFS_LAST_PATH = "LastPath";
  private static String SEPARATOR = "|";

  private final Image zipImage =
      new Image (getClass ().getResourceAsStream ("/icons/zip-icon.png"));
  private final Image diskImage = new Image (getClass ().getResourceAsStream ("/icons/disk.png"));
  private final Image folderImage =
      new Image (getClass ().getResourceAsStream ("/icons/folder-icon.png"));
  //  private final Image xImage =
  //      new Image (getClass ().getResourceAsStream ("/icons/X-green-icon.png"));
  //  private final Image mImage =
  //      new Image (getClass ().getResourceAsStream ("/icons/M-blue-icon.png"));
  //  private final Image dImage =
  //      new Image (getClass ().getResourceAsStream ("/icons/D-pink-icon.png"));
  //  private final Image tImage =
  //      new Image (getClass ().getResourceAsStream ("/icons/T-black-icon.png"));

  private Font font;

  private final MultipleSelectionModel<TreeItem<TreeFile>> model = getSelectionModel ();
  private final List<TreeNodeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public AppleTreeView (AppleTreeItem root)
  // ---------------------------------------------------------------------------------//
  {
    super (root);

    model.selectedItemProperty ().addListener ( (obs, oldSel, newSel) ->
    {
      if (newSel == null)
      {
        System.out.println ("Should never happen - newSel is null");
        return;
      }

      TreeFile treeFile = newSel.getValue ();

      // same test as in AppleTreeItem.getChildren()
      // when item selection happens first we do this one
      if (treeFile.isLocalFile () && !treeFile.isAppleFileSystem ())
        treeFile.setAppleFile (factory.getFileSystem (treeFile.getFile ()));

      for (TreeNodeListener listener : listeners)
        listener.treeNodeSelected (treeFile);
    });

    setCellFactory (new Callback<TreeView<TreeFile>, TreeCell<TreeFile>> ()
    {
      @Override
      public TreeCell<TreeFile> call (TreeView<TreeFile> parm)
      {
        TreeCell<TreeFile> cell = new TreeCell<> ()
        {
          private final ImageView imageView = new ImageView ();

          public void updateItem (TreeFile treeFile, boolean empty)
          {
            super.updateItem (treeFile, empty);

            if (empty || treeFile == null)
            {
              setText (null);
              setGraphic (null);
            }
            else
            {
              setText (treeFile.getName ());
              setImageView (treeFile);
              setGraphic (imageView);
              setFont (font);
            }
          }

          private void setImageView (TreeFile treeFile)
          {
            if (treeFile.isCompressedLocalFile ())
              imageView.setImage (zipImage);
            else if (treeFile.isLocalDirectory ())
              imageView.setImage (folderImage);
            else if (treeFile.isLocalFile ())
              imageView.setImage (diskImage);
            else
              imageView.setImage (null);
          }
        };

        return cell;
      }
    });
  }

  // ---------------------------------------------------------------------------------//
  public void setRootFolder (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    setRoot (appleTreeItem);
    appleTreeItem.setExpanded (true);
  }

  // ---------------------------------------------------------------------------------//
  Optional<TreeItem<TreeFile>> getNode (String path)
  // ---------------------------------------------------------------------------------//
  {
    TreeItem<TreeFile> node = getRoot ();
    Optional<TreeItem<TreeFile>> optionalNode = Optional.empty ();

    String[] chunks = path.split ("\\" + SEPARATOR);

    for (int i = 2; i < chunks.length; i++)
    {
      model.select (node);
      optionalNode = search (node, chunks[i]);
      if (!optionalNode.isPresent ())
        break;
      node = optionalNode.get ();
    }

    setShowRoot (false);        // workaround for stupid javafx bug
    return optionalNode;
  }

  // ---------------------------------------------------------------------------------//
  private Optional<TreeItem<TreeFile>> search (TreeItem<TreeFile> parentNode, String name)
  // ---------------------------------------------------------------------------------//
  {
    parentNode.setExpanded (true);

    for (TreeItem<TreeFile> childNode : parentNode.getChildren ())
      if (childNode.getValue ().getName ().equals (name))
        return Optional.of (childNode);

    return Optional.empty ();
  }

  // ---------------------------------------------------------------------------------//
  String getSelectedItemPath ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder pathBuilder = new StringBuilder ();

    TreeItem<TreeFile> item = model.getSelectedItem ();
    while (item != null)
    {
      pathBuilder.insert (0, SEPARATOR + item.getValue ().getName ());
      item = item.getParent ();
    }

    return pathBuilder.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.put (PREFS_LAST_PATH, getSelectedItemPath ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    String lastPath = prefs.get (PREFS_LAST_PATH, "");

    if (!lastPath.isEmpty ())
    {
      Optional<TreeItem<TreeFile>> optionalNode = getNode (lastPath);
      if (optionalNode.isPresent ())
      {
        int row = getRow (optionalNode.get ());
        model.select (row);
        scrollTo (model.getSelectedIndex ());
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    this.font = font;
    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void addListener (TreeNodeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  interface TreeNodeListener
  // ---------------------------------------------------------------------------------//
  {
    public void treeNodeSelected (TreeFile treeFile);
  }
}
