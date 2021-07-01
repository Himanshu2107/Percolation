# Percolation
Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

### The Model
We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

### The Problem
 In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates? When p equals 0, the system does not percolate; when p equals 1, the system percolates. When n is sufficiently large, there is a threshold value p* such that when p < p* a random n-by-n grid almost never percolates, and when p > p*, a random n-by-n grid almost always percolates. There is no mathematical formulation for p* hence we estimate p* using computer simulations.
 
 ### This Program
 To estimate p* we can use Monte - Carlo simulations. Consider the following computational experiment:
 - Initialize all sites to be blocked.
 - Repeat the following until the system percolates:
     - Choose a site uniformly at random among all blocked sites.
     - Open the site.
 - The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate of the percolation threshold.

This program consists of two java classes `Percolation` and `PercolationStats`. `Percolation` models a percolation system with all sites initially blocked. It contains methods to open a particular site, find if a site is open or full, and to know if a system percolates. We use the union find data structure to make these operatioons more efficient. `PercolationStats` is responsible for actually performing random simulations, and calculates the mean value of p* along with the 95% confidence interval for given value of *n*.

#### Compilation
- `javac Percolation.java`
- `javac PercolationStats.java` <br>
Note - It is important that `Percolation` is compiled before `PercolationStats`.

#### Usage
Run `java PercolationStats n trials` in order to estimate the p* value for an *n-by-n* grid using `trials` number of trials.
