# 🧬 GRAPHS-IN-BIOINFORMATICS

A comprehensive Java-based tool designed to analyze **Protein-Protein Interaction (PPI)** networks using the STRING database. This project implements a high-performance **Graph Abstract Data Type (ADT)** to model biological data, filter interactions by confidence, and calculate complex network metrics.

## 🚀 Overview & Features

This tool provides a robust command-line interface (CLI) for researchers and developers to explore biological networks. Key capabilities include:

1.  **Advanced Graph Construction:**
    * Parses and loads massive datasets from the STRING database.
    * **Dynamic Filtering:** Users can filter interactions based on a custom **Confidence Score Threshold** (0.0 - 1.0) to focus on high-reliability connections.
2.  **Protein Identification:** efficiently searches and retrieves specific proteins by their unique identifiers.
3.  **Interaction Verification:** Instantly checks for direct physical or functional associations between any two proteins.
4.  **Reliability Pathfinding:** Implements algorithms to determine the "Most Confident Path" between proteins, prioritizing high-score interactions.
5.  **Network Topology Analysis:** Calculates essential graph metrics to understand network structure:
    * Vertex & Edge Counts
    * Average Degree
    * Network Diameter
    * Reciprocity
6.  **Graph Traversals:** Supports standard BFS (Breadth-First Search) and DFS (Depth-First Search) algorithms for network exploration.

## ⚠️ Dataset Configuration (Required)

To maintain a lightweight repository, the large biological datasets (600MB+) are not hosted directly here. Users must obtain the official STRING dataset for *Homo sapiens*.

**Setup Instructions:**

1.  **Download Source Data:** Visit the [STRING v12.0 Database](https://string-db.org/cgi/download?species_text=Homo+sapiens).
2.  **Acquire Files:** Download the following:
    * `9606.protein.links.v12.0.txt.gz` (Interaction Network)
    * `9606.protein.info.v12.0.txt.gz` (Protein Metadata)
3.  **Installation:**
    * Extract/Unzip the `.gz` archives.
    * Place the resulting `.txt` files into the `resources/` directory in the project root.
    * **Ensure exact filenames:**
        * `resources/9606.protein.info.v12.0.txt`
        * `resources/9606.protein.links.v12.0.txt`

## 🛠 Project Architecture

```text
GRAPHS-IN-BIOINFORMATICS/
├── resources/                  # External datasets (Git ignored / User provided)
│   ├── 9606.protein.info.v12.0.txt
│   └── 9606.protein.links.v12.0.txt
├── src/                        # Core Application Logic
│   ├── Edge.java               # Weighted interaction model
│   ├── Graph.java              # Graph ADT implementation (Edge List)
│   ├── GraphADT.java           # Interface definition
│   ├── Protein.java            # Node entity
│   └── Main.java               # CLI entry point
└── README.md
