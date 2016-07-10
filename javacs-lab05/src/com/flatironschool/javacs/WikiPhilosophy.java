package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();

	public static void main(String[] args) throws IOException {
    	String currentPage = "/wiki/Pythagoras";
    	ArrayList<String> visited = new ArrayList<String>();
    	visited.add(currentPage);
    	outerloop: while (true) {
    		String url = "https://en.wikipedia.org" + currentPage;

			innerloop: for (int j=0;j<=3;j++) {
				Elements paragraphs = wf.fetchWikipedia(url);
				System.out.println("we are currently at "+ url);

				Element para = paragraphs.get(j);
				Iterable<Node> iter = new WikiNodeIterable(para);
				
				int openCounter = 0;
				int closedCounter = 0;

				for (Node node: iter) {
					if (node instanceof Element) {
						if (node.hasAttr("href")) {
							if(!(node.parent().toString().charAt(1) == 'i')) {
								String link = node.attr("href");
								if (!(link.charAt(0) == '#')) {
									if (openCounter == closedCounter){
										if (!visited.contains(link)){
											currentPage = link;
											visited.add(currentPage);
											if (currentPage.equals("/wiki/Philosophy")) {
												System.out.println("We are currently at https://en.wikipedia.org/wiki/Philosophy");
								    			System.out.println("WE MADE IT IN " + visited.size() + " STEPS!!");
								    			break outerloop;
								    		}
								    		break innerloop;
								    	}
									}
								}
							}
						}
					} else if (node instanceof TextNode) {
						String text = node.toString();
						for (int i = 0; i < text.length(); i++){
							if (text.charAt(i) == '('){
								openCounter++;
							}
							else if (text.charAt(i) == ')'){
								closedCounter++;
							}
						}
					}
		        }
			}
    	}
    }
}
