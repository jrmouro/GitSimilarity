set title 'GitMining - ChangedFiles'
set xlabel 'tempo'
set ylabel 'volume'
set grid
set xrange [0.0:1.0]
set yrange [0.0:1.0]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (((log(x)/((1.0)+(1.0)+0.0)/1.0)*(sin(x)-((1.0)**2.0**1.0)-0.0)*1.0)*(sin(165.49108731373767)*((sin(x)**(1.0)**1.0)**cos(-17.85520023411795)**1.0)*1.0)*1.0)
plot f(x) title '(((log(x)/((1.0)+(1.0)+0.0)/1.0)*(sin(x)-((1.0)**2.0**1.0)-0.0)*1.0)*(sin(165.49108731373767)*((sin(x)**(1.0)**1.0)**cos(-17.85520023411795)**1.0)*1.0)*1.0)', '/home/ronaldo/Documentos/GitSimilarity/temp.txt' w p ls 1 title 'points'