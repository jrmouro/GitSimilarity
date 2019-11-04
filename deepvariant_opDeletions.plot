set title 'deepvariant - Deletions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = sin((sin(-1.0) + cos(0.5274009303430505)))
plot f(x) title 'sin((sin(-1.0) + cos(0.5274009303430505)))', '/home/ronaldo/Documentos/GitSimilarity/deepvariant_deletionsData.txt' w p ls 1 title 'points'