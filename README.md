# 🧬 PPI Network Analyzer (Bioinformatics Graph ADT)

A Java-based application that analyzes **Protein-Protein Interaction (PPI)** networks using the STRING database. This project implements a **Graph Abstract Data Type (ADT)** using an Edge List structure to process biological data, visualize relationships, and calculate network metrics.

*Developed for CME 2201 - Data Structures and Algorithms*

## 🚀 Features

The application provides a console-based interface with the following capabilities:

1.  **Graph Construction & Filtering:**
    * Loads protein data and interactions from the STRING dataset.
    * [cite_start]Filters interactions based on a user-defined **Confidence Score Threshold** (0.0 - 1.0)[cite: 15, 62].
2.  [cite_start]**Protein Search:** Find specific proteins by their ID[cite: 64].
3.  [cite_start]**Interaction Verification:** Check if two proteins have a direct physical or functional association[cite: 65].
4.  [cite_start]**Pathfinding:** Algorithms to find the "Most Confident Path" between two proteins[cite: 66].
5.  [cite_start]**Network Metrics:** Calculates key graph statistics[cite: 67]:
    * Vertex & Edge Counts
    * Average Degree
    * Network Diameter
    * Reciprocity
6.  [cite_start]**Traversals:** Perform BFS (Breadth-First Search) and DFS (Depth-First Search) starting from a specific protein[cite: 68].

## ⚠️ Dataset Setup (Important)

Due to GitHub's file size limits, the large interaction datasets (600MB+) are not hosted in this repository. You must download the official STRING dataset for *Homo sapiens*.

**Steps to Configure:**

1.  **Download Data:** Visit the [STRING v12.0 Download Page](https://string-db.org/cgi/download?species_text=Homo+sapiens).
2.  **Get these two files:**
    * `9606.protein.links.v12.0.txt.gz` (Interaction data)
    * `9606.protein.info.v12.0.txt.gz` (Protein metadata)
3.  **Extract & Place:**
    * Unzip the `.gz` files.
    * Place the resulting `.txt` files into the `resources/` folder in the project root.
    * **Verify filenames exactly:**
        * `resources/9606.protein.info.v12.0.txt`
        * `resources/9606.protein.links.v12.0.txt`

## 🛠 Project Structure

```text
PPI-Network-Analyzer/
├── resources/                  # Place downloaded .txt files here
│   ├── 9606.protein.info.v12.0.txt
│   └── 9606.protein.links.v12.0.txt
├── src/
│   ├── Edge.java               # Represents interaction with weight
│   ├── Graph.java              # Main graph logic (Adjacency List/Edge List)
│   ├── GraphADT.java           # Interface definition
│   ├── Protein.java            # Node entity
│   └── Main.java               # CLI Menu and entry point
└── README.md
