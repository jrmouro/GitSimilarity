set title 'Mining-the-Social-Web - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = cos(((exp(3.14159265359) / (1.0 - 3.14159265359)) - exp(-1.0)))
plot f(x) title 'cos(((exp(3.14159265359) / (1.0 - 3.14159265359)) - exp(-1.0)))', '/home/ronaldo/Documentos/GitSimilarity/Mining-the-Social-Web_changedFilesData.txt' w p ls 1 title 'points'