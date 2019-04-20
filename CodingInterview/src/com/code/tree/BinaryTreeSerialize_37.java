package com.code.tree;

public class BinaryTreeSerialize_37 {
    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }
    int index = -1;   //计数变量
//    private String Serialize(TreeNode root) {
//        StringBuffer stringBuffer=new StringBuffer();
//        if(root==null){
//            return stringBuffer.append("#,").toString();
//        }
//        stringBuffer.append(root.val+",");
//        stringBuffer.append(Serialize(root.left));
//        stringBuffer.append(Serialize(root.right));
//        return stringBuffer.toString();
//    }
//
//    private TreeNode Deserialize(String str) {
//       index++;
//       int length=str.length();
//       if(index>=length)
//           return null;
//       String[] strr=str.split(",");
//       TreeNode node=null;
//       if(!strr[index].equals("#")){
//           node=new TreeNode(Integer.valueOf(strr[index]));
//           node.left=Deserialize(str);
//           node.right=Deserialize(str);
//       }
//       return node;
//    }

    private String Serialize(TreeNode root) {
        StringBuffer buffer=new StringBuffer();
        if(root==null)
            return buffer.append("#,").toString();
        buffer.append(root.val+",");
        buffer.append(Serialize(root.left));
        buffer.append(Serialize(root.right));
        return buffer.toString();
    }

    private TreeNode Deserialize(String str) {
        index++;
        int length=str.length();
        if(index>length)
            return null;
        String[] strr=str.split(",");
        TreeNode node=null;
        if(!strr[index].equals("#")){
            node=new TreeNode(Integer.valueOf(strr[index]));
            node.left=Deserialize(str);
            node.right=Deserialize(str);
        }
        return node;
    }
}
