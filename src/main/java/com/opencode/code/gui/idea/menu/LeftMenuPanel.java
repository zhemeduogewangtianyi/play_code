package com.opencode.code.gui.idea.menu;

import com.opencode.code.gui.idea.frame.IdeaFrame;
import com.opencode.code.gui.idea.resource.FileManager;
import com.opencode.code.gui.idea.text.IdeaTextAreaPanel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

public class LeftMenuPanel extends JPanel {

    private final Map<String,String> map = new LinkedHashMap<>();

    private final DefaultMutableTreeNode root;

    private final DefaultTreeModel model;

    private final IdeaFrame ideaFrame;

    private JTree jTree;


    public LeftMenuPanel(IdeaFrame ideaFrame){

        this.ideaFrame = ideaFrame;
        super.setPreferredSize(new Dimension(160,600));

        root = new DefaultMutableTreeNode("project");

        model = new DefaultTreeModel(root);

        jTree = new JTree(model);



        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("关闭");
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "确定关闭？", "关闭", JOptionPane.YES_NO_OPTION);
                if(res == JOptionPane.YES_OPTION){

                    TreePath treePath = jTree.getAnchorSelectionPath();

                    if(treePath == null){
                        return;
                    }

                    jTree.setSelectionPath(treePath);

                    DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode)treePath.getLastPathComponent();
                    if(lastPathComponent.getParent() == null){
                        return;
                    }
                    map.remove(lastPathComponent.getUserObject().toString());
                    root.remove(lastPathComponent);
//                    jTree.updateUI();
                    model.reload();
                    ideaFrame.getIdeaTextAreaPanel().setText("");
                    ideaFrame.getIdeaTextAreaPanel().setFileName(null);
                    ideaFrame.getIdeaTextAreaPanel().setDir(null);
                    ideaFrame.getBottomConsolePanel().setText("控制台~");
                }
            }
        });
        jPopupMenu.add(delete);
        jTree.add(jPopupMenu);

        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int modifiers = e.getModifiers();

                TreePath treePath = jTree.getPathForLocation(e.getX(), e.getY());

                if(treePath == null){
                    return;
                }

                jTree.setSelectionPath(treePath);

                DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode)treePath.getLastPathComponent();
                if(lastPathComponent.getParent() == null){
                    return;
                }
                String userObject = lastPathComponent.getUserObject().toString();
                String dir = map.get(userObject);
                if(dir == null || dir.equals("")){
                    return;
                }
                IdeaTextAreaPanel ideaTextAreaPanel = ideaFrame.getIdeaTextAreaPanel();

                ideaTextAreaPanel.setDir(dir);
                ideaTextAreaPanel.setFileName(userObject);
                String text = FileManager.fileLoad(dir, userObject);
                ideaTextAreaPanel.setText(text);

                if(modifiers == 4){
                    jPopupMenu.show(e.getComponent(),e.getX(),e.getY());
                }

            }
        });

        this.add(jTree);


    }

    public boolean addFirstNode(String dir,String filename){
        if(dir == null || filename == null){
            return false;
        }
        if(map.containsKey(filename)){
            return false;
        }
        map.put(filename,dir);
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(filename);
        model.insertNodeInto(node1,root,root.getChildCount());
        model.reload();
        TreeNode[] pathToRoot = model.getPathToRoot(node1);
        jTree.setSelectionPath(new TreePath(pathToRoot));
        return true;
    }


}
