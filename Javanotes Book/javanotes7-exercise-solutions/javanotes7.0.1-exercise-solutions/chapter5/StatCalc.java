/* 
      * An object of class StatCalc can be used to compute several simple statistics
      * for a set of numbers.  Numbers are entered into the dataset using
      * the enter(double) method.  Methods are provided to return the following
      * statistics for the set of numbers that have been entered: The number
      * of items, the sum of the items, the average, the standard deviation,
      * the maximum, and the minimum.
      */
     public class StatCalc {
     
        private int count;   // Number of numbers that have been entered.
        private double sum;  // The sum of all the items that have been entered.
        private double squareSum;  // The sum of the squares of all the items.
        private double max = Double.NEGATIVE_INFINITY;  // Largest item seen.
        private double min = Double.POSITIVE_INFINITY;  // Smallest item seen.
     
        /**
         * Add a number to the dataset.  The statistics will be computed for all
         * the numbers that have been added to the dataset using this method.
         */
        public void enter(double num) {
           count++;
           sum += num;
           squareSum += num*num;
           if (num > max)
              max = num;
           if (num < min)
              min = num;
        }
     
        /**
         * Return the number of items that have been entered into the dataset.
         */
        public int getCount() {
           return count;
        }
     
        /**
         * Return the sum of all the numbers that have been entered.
         */
        public double getSum() {
           return sum;
        }
     
        /**
         * Return the average of all the items that have been entered.
         * The return value is Double.NaN if no numbers have been entered.
         */
        public double getMean() {
           return sum / count;  
        }
     
        /**
         * Return the standard deviation of all the items that have been entered.
         * The return value is Double.NaN if no numbers have been entered.
         */
        public double getStandardDeviation() {  
           double mean = getMean();
           return Math.sqrt( squareSum/count - mean*mean );
        }
        
        /**
         * Return the smallest item that has been entered.
         * The return value will be infinity if no numbers have been entered. 
         */
        public double getMin() {
           return min;
        }
        
        /**
         * Return the largest item that has been entered.
         * The return value will be -infinity if no numbers have been entered. 
         */
        public double getMax() {
           return max;
        }
     
     }  // end class StatCalc