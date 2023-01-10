set terminal svg background rgb "white"
set output "./triPuisRecherche.svg"
set grid
set key below
set xlabel "nombre de recherches (dans un tableau de taille 100.000)"
set ylabel "nombre de comparaisons et d'échanges"
set pointsize 2
set style line 1 linetype 1 lw 1 linecolor rgb "red" pointtype 1
set style line 2 linetype 1 lw 1 linecolor rgb "blue" pointtype 1
set style line 3 linetype 1 lw 1 linecolor rgb "web-green" pointtype 1
set style line 4 linetype 1 lw 1 linecolor rgb "orange" pointtype 1
plot \
"./benchmarkRechercheSansTri.txt" using 1:2 \
  title "Chercher sans trier" with linespoints linestyle 1, \
"./benchmarkTriSelectionBouclePour.txt" using 1:2 \
  title "Trier (sélection) puis chercher (boucle Pour)" with linespoints linestyle 2, \
"./benchmarkTriSelectionBoucleTantQue.txt" using 1:2 \
  title "Trier (sélection) puis chercher (boucle Tant Que)" with linespoints linestyle 3, \
"./benchmarkTriSelectionRechercheDicho.txt" using 1:2 \
  title "Trier (sélection) puis chercher (dichotomie)" with linespoints linestyle 4
