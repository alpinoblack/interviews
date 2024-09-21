package com.example.interviews.general.trees.binary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.com/problems/serialize-and-deserialize-binary-tree/">...</a>
 */
public class Codec {

  // Encodes a tree to a single string.
  public String serialize(TreeNode root) {
    final var treeRepresentedAsArr = new ArrayList<Integer>(10);
    serializeAux(treeRepresentedAsArr, root, 1);

    return String.join(",", treeRepresentedAsArr
        .stream().map(Object::toString).toList());
  }

  private void serializeAux(final List<Integer> intermediateSerializedTreeRep,
      final TreeNode root, final int nodeCounter) {
    if (root != null) {
      if (intermediateSerializedTreeRep.size() < nodeCounter) {
        for (int i = intermediateSerializedTreeRep.size() + 1; i < nodeCounter; i++) {
          intermediateSerializedTreeRep.add(-1001);
        }
      }
      intermediateSerializedTreeRep.add(nodeCounter - 1, root.getVal());
      serializeAux(intermediateSerializedTreeRep, root.getLeft(), nodeCounter * 2);
      serializeAux(intermediateSerializedTreeRep, root.getRight(), nodeCounter * 2 + 1);
    }
  }

  private TreeNode deserializeAux(final List<Integer> treeRep,
      final int nodeCounter) {

    if (treeRep.size() >= nodeCounter && treeRep.get(nodeCounter - 1) != -1001) {
      final var newNode = new TreeNode(treeRep.get(nodeCounter - 1));
      newNode.setLeft(deserializeAux(treeRep, nodeCounter * 2));
      newNode.setRight(deserializeAux(treeRep, nodeCounter * 2 + 1));
      return newNode;
    }
    return null;
  }

  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    final List<Integer> intermediateTreeRepresentation =
        Arrays.stream(data.split(","))
            .filter(a -> !a.isEmpty())
            .map(Integer::parseInt)
            .toList();
    if (intermediateTreeRepresentation.isEmpty()) {
      return null;
    } else {
      return deserializeAux(intermediateTreeRepresentation, 1);
    }
  }

  public static void main(String[] args) {
    final var codec = new Codec();
    final var root = new TreeNode(1);
    root.setLeft(new TreeNode(2));
    root.setRight(new TreeNode(3));
    root.getRight().setLeft(new TreeNode(4));
    root.getRight().setRight(new TreeNode(5));
    final var serializedTree = codec.serialize(root);
    final TreeNode deserializedTree = codec.deserialize(serializedTree);
    System.out.println(serializedTree);
    System.out.println(deserializedTree);
  }

}
