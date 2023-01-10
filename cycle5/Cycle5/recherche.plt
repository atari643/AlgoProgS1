set terminal svg background rgb "white"
set output "./recherche.svg"
set grid
set key below
set xlabel "taille du tableau"
set ylabel "nombre de comparaisons"
set pointsize 2
set style line 1 linetype 1 lw 1 linecolor rgb "red" pointtype 1
set style line 2 linetype 1 lw 1 linecolor rgb "blue" pointtype 1
set style line 3 linetype 1 lw 1 linecolor rgb "web-green" pointtype 1
plot \
"./benchmarkRechercheBouclePour.txt" using 1:2 \
  title "Recherche for" with linespoints linestyle 1, \
"./benchmarkRechercheBoucleTantQue.txt" using 1:2 \
  title "Recherche while" with linespoints linestyle 2, \
"./benchmarkRechercheDichotomique.txt" using 1:2 \
  title "Recherche dichotomique" with linespoints linestyle 3.
