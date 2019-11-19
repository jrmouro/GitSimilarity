set title 'tpot - Deletions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = ((sin((1.0 - 0.7777742536141842)) * 0.7777742536141842) * (x / (0.7777742536141842 + (log((x + x)) + x))))
plot f(x) title '((sin((1.0 - 0.7777742536141842)) * 0.7777742536141842) * (x / (0.7777742536141842 + (log((x + x)) + x))))', '/home/ronaldo/Documentos/GitSimilarity/tpot_deletionsData.txt' w p ls 1 title 'points'