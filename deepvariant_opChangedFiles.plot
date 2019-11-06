set title 'deepvariant - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (0.5924251012188884 - sin(sin(0.5924251012188884)))
plot f(x) title '(0.5924251012188884 - sin(sin(0.5924251012188884)))', '/home/ronaldo/Documentos/GitSimilarity/deepvariant_changedFilesData.txt' w p ls 1 title 'points'