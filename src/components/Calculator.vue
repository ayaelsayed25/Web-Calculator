<template>
  <div class="myapp" >
    <div class="calculator">
      <div id = "status">{{status || 'Web Calculator'}}</div>
      <div id = "previous-op">{{previousOp}}</div>
      <div id = "text">{{display || '0'}}</div>
      <div id = "btns" class="buttons">
        <button class="btn" @click="clear">C</button>
        <button class="btn" @click="clearE">CE</button>
        <button class="btn single-op" @click="percent">%</button>
        <button id="back" class="btn" @click="backspace">Backspace</button>
        <button class="btn single-op" @click="fraction">1/x</button> 
        <button class="btn single-op" @click="squared">x²</button>
        <button class="btn single-op" @click="root">√x</button>
        <button class="btn operation" @click="operation($event)">÷</button>
        <button class="btn number" @click="write($event)">7</button>
        <button class="btn number" @click="write($event)">8</button>
        <button class="btn number" @click="write($event)">9</button>
        <button class="btn operation" @click="operation($event)">×</button>
        <button class="btn number" @click="write($event)">4</button>
        <button class="btn number" @click="write($event)">5</button>
        <button class="btn number" @click="write($event)">6</button>
        <button class="btn operation" @click="operation($event)">-</button>
        <button class="btn number" @click="write($event)">1</button>
        <button class="btn number" @click="write($event)">2</button>
        <button class="btn number" @click="write($event)">3</button>
        <button class="btn operation" @click="operation($event)">+</button>
        <button class="btn single-op" @click="sign">+/-</button>
        <button class="btn number" @click="write($event)">0</button>
        <button class="btn single-op" @click="write($event)">.</button>
        <button id= "equal" class="btn" @click="equal">=</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'Calculator',
  data(){
    return {
      display: '',
      previousOp: '',
      firstOperand : '',
      secondOperand : '',
      operator :'',
      singleOpCheck: false,
      status: ''
   }
  },
  methods: {
    clear(){
      this.display = '';
      this.previousOp = '';
      this.firstOperand = '';
      this.secondOperand = '';
      this.operator ='';
      this.singleOpCheck = false;
    },
    clearE()
    {
      this.display = '';
      this.singleOpCheck = false;
    },
    checkDisplay()
    {
      if(this.display === '')
      {
        this.display = '0';
      }
    },
    sign(){
       if(this.display.charAt(0) == '-')
       {
         this.display = this.display.slice(1, this.display.length);
       }
       else
       {
         this.display = "-" + this.display;
       }
    },
    percent(){
      if(!this.singleOpCheck)
      {
        this.checkDisplay();
        this.display = "(" + this.display + ")%";
        this.singleOpCheck = true;
      }
    },
    backspace()
    {
      var ch = (this.display).charAt(this.display.length - 1);
      if(!isNaN(ch) || ch === "-" || ch === "." )
      {
        this.display = this.display.slice(0, -1);
      }
      else
      {
         axios.get('http://localhost:8080/backSpace', {
          params: {
              operand: this.display
          }
      })
          .then((response) => {
            this.display = (response.data).toString();
            this.singleOpCheck = false;
          })
          .catch(function (error) {
              console.log(error);
          });  
      }
    },
    fraction()
    {
      if(!this.singleOpCheck)
      {
        this.checkDisplay();
        this.display = "(1/" + this.display + ")";
        this.singleOpCheck = true;
      }
    },
    squared()
    {
      if(!this.singleOpCheck)
      {
        this.checkDisplay();
        this.display = "(" + this.display + ")²";
        this.singleOpCheck = true;
      }
    },
    root()
    {
      if(!this.singleOpCheck)
      {
        this.checkDisplay();
        this.display = "√(" + this.display + ")";
        this.singleOpCheck = true; 
      }
    },
    write(e)
    {
      if(!this.singleOpCheck)
      {
        this.display = this.display + e.target.innerHTML;
      }
    },
    checkPrevious()
    {
      var bool = this.previousOp.includes("+") || this.previousOp.includes("-") || this.previousOp.includes("×") ||this.previousOp.includes("÷");
      return bool && (this.display === '');
    },
    operation(e)
    {
      if(this.checkPrevious())
      {
        this.display = '0';
      }
      if(this.previousOp ==='')
        {
          this.firstOperand = this.display;
          this.operator = e.target.innerHTML;
          this.previousOp = this.display + " " +this.operator;
          this.display = '';
        } 
       else{
          this.secondOperand = this.display;
           axios.get('http://localhost:8080/operation', {
          params: {
              firstOperand: this.firstOperand,
              secondOperand: this.secondOperand,
              operator: this.operator
          }
      })
          .then((response) => {
            if(response.data == "Error")
            {
              this.clear();
              this.status = 'Error Occured';
            }
            else if(((response.data).toString()).includes("e"))
            {
              this.clear();
              this.status = 'Numbers are too big to be manipulated';
            }
            else{
              this.status = '';
              this.previousOp = response.data + " " + e.target.innerHTML;
              console.log(response.data);
              this.operator = e.target.innerHTML;
              this.firstOperand = (response.data).toString();
              this.display = '';
            }
          })
          .catch(function (error) {
              console.log(error);
          });  
          
        }
        this.singleOpCheck = false;
    },

    equal()
    {
      if(this.checkPrevious())
      {
        this.display = '0';
      }
      this.secondOperand = this.display;
      axios.get('http://localhost:8080/operation', {
          params: {
              firstOperand: this.firstOperand,
              secondOperand: this.secondOperand,
              operator: this.operator
          }
      })
          .then((response) => {
            if(response.data == "Error")
            {
              this.clear();
              this.status = 'Error Occured';
            }
            else if(((response.data).toString()).includes('e'))
            {
              this.clear();
              this.status = 'Numbers are too big to be manipulated';
            }
            else{
              this.status = '';
              this.display = (response.data).toString();
              this.previousOp = '';
              this.singleOpCheck = false;
              console.log(response.data);
            }
          })
          .catch(function (error) {
              console.log(error);
          });  
    }
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.myapp {
  position: absolute;
  margin: 0;
  padding: 0;
  width: 100vw;
  height: 101%;
  margin-left: -0.62%;
  margin-top: -1%;
  background-color: #ccc;
}
.calculator {
  position: absolute;
  transform: translate(-50%,-50%);
  top: 60%;
  left: 50%;
}
.buttons {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  width: 313px;
  height: 350px;
}

#previous-op {
  width: 280px;
  height: 27px;
  background-color: white;
  border-left:2px solid rgb(141, 136, 136) ;
  border-right:2px solid  rgb(141, 136, 136);
  border-top:none;
  border-bottom: none;
  font-size: 20px;
  padding: 15px;
  color: rgb(94, 91, 91);
  overflow: auto;
}
#text {
  width: 280px;
  height: 40px;
  background-color: white;
  border-left:2px solid rgb(141, 136, 136) ;
  border-right:2px solid  rgb(141, 136, 136);
  border-bottom: none;
  border-top:none;
  font-size: 22px;
  padding: 15px;
  outline: none;
  color: rgb(61, 59, 59);
  font-weight: 600;
  overflow: auto;
}
#status {
  width: 289px;
  height: 7px;
  background-color:#ff5bd6;
  border-left:2px solid rgb(141, 136, 136) ;
  border-right:2px solid  rgb(141, 136, 136);
  border-bottom: none;
  border-top:2px solid rgb(141, 136, 136) ;;
  font-size: 15px;
  padding: 10px;
  padding-top: 0;
  color: white;
}
.calculator button {
  border: 1px #ccc solid;
  font-weight: 600;
  font-size: 17px;
  color:rgb(61, 59, 59) ;
  outline: none;
  cursor: pointer;
}
.number {
  background-color: white;
}
#equal {
  background-color: #17ff0f;
}
#back {
  background-color: #ff5bd6;
}
.calculator button:hover {
  background-color: rgb(50, 180, 231);
}
#equal:hover, #back:hover {
  background-color: rgb(50, 180, 231);
}

</style>
