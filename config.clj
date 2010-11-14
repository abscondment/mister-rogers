{
 ;; File is rows of space-delimited id, lat, and lon.
 :data-file "seattle-sample.dat"
 ;; Attepmt to re-read the data file every 5 minutes
 :data-update-interval 300
 ;; Return only the nearest neighbor if size is unspecified.
 :default-result-size 1
}