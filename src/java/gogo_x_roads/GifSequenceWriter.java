package gogo_x_roads;


import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;

public class GifSequenceWriter {
  protected ImageWriter gifWriter;
  protected ImageWriteParam imageWriteParam;
  protected IIOMetadata imageMetaData;
  protected ImageOutputStream outputStream;

  public GifSequenceWriter(ImageOutputStream outputStream, int imageType, int delay, boolean loop) throws IOException {
    gifWriter = getWriter();
    imageWriteParam = gifWriter.getDefaultWriteParam();
    ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
    imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);
    configureRootMetadata(delay, loop);
    gifWriter.setOutput(outputStream);
    gifWriter.prepareWriteSequence(null);
  }

  private void configureRootMetadata(int delay, boolean loop) throws IIOInvalidTreeException {
    String metaFormatName = imageMetaData.getNativeMetadataFormatName();
    IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);
    IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
    graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10));
    graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
    graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");

    if (loop) {
      IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
      IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
      child.setAttribute("applicationID", "NETSCAPE");
      child.setAttribute("authenticationCode", "2.0");
      child.setUserObject(new byte[]{0x1, (byte) (0), (byte) (0)});
      appExtensionsNode.appendChild(child);
    }

    imageMetaData.setFromTree(metaFormatName, root);
  }

  private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
    for (int i = 0; i < rootNode.getLength(); i++) {
      if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
        return (IIOMetadataNode) rootNode.item(i);
      }
    }
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    rootNode.appendChild(node);
    return node;
  }

  public void writeToSequence(RenderedImage img) throws IOException {
    gifWriter.writeToSequence(new IIOImage(img, null, imageMetaData), imageWriteParam);
  }

  public void close() throws IOException {
    gifWriter.endWriteSequence();
    outputStream.close();
  }

  private static ImageWriter getWriter() throws IIOException {
    Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
    if (!iter.hasNext()) throw new IIOException("No GIF writers!");
    return iter.next();
  }
}