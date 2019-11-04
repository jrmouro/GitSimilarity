set title 'node-open-mining-portal - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = log(1.0)
plot f(x) title 'log(1.0)', '/home/ronaldo/Documentos/GitSimilarity/node-open-mining-portal_changedFilesData.txt' w p ls 1 title 'points'