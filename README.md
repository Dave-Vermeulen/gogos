# g0g0's X Roads 🌟

**Empowering the Youth Through Tech, Wellness, and Community.**  
*g0g0's X Roads* is a FOSS (Free and Open Source Software) initiative that combines STEM education, gamification, and sustainability to uplift youth from disadvantaged communities. This dynamic web application is the heart of our mission.

---

## 🌐 Live Demo
🚀 **Coming Soon** on [Vercel](https://vercel.com)! Stay tuned!

---

## 📖 Project Overview

**Landing Page**  
- A quirky, dynamic **landing page** featuring a dancing coffee cup, clickable with a secret puzzle.  
- Mystical aesthetics using a **coffee-inspired palette** with **Islamic green highlights** for interactive elements.

**User Authentication**  
- Secure user account creation and login functionality with hashed passwords.  
- Fully integrated SQLite database for persistent storage.

**About Page**  
- A vibrant and interactive overview of our programs:  
  - **MoveIT**: Embrace wellness through hiking, yoga, and dance.  
  - **CodeIT**: Gamify learning computer science and programming.  
  - **GrowIT**: Build sustainable gardens for feeding schemes.  
  - **MakeIT**: Gain real-world skills in tech operations and creativity.

---

## 🛠️ Tech Stack

**Back-End**:  
- **Clojure**: Dynamic web app generation using Compojure, Hiccup, and Ring.  
- **SQLite**: Persistent database storage.  
- **Buddy**: Password hashing and authentication.

**Front-End**:  
- **HTML5** and **Hiccup**: For clean and responsive page designs.  
- **CSS3**: Modern, vibrant styling.  
- **JavaScript**: Interactivity for features like the click puzzle.

**Additional Tools**:  
- **Java Utility**: `GifSequenceWriter` for programmatically generating animated GIFs.  
- **Leiningen**: Dependency and build management.

---

## 🚀 Quick Start Guide

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/gogo-x-roads.git
cd gogo-x-roads
```
### 2️⃣ Start the REPL
```bash
lein repl
(require 'gogo-x-roads.core)
(in-ns 'gogo-x-roads.core)
(-main) ;; Launch the server
```

### 3️⃣ Open in Your Browser
```bash
(require 'gogo-x-roads.gif)
(gogo-x-roads.gif/create-coffee-dance-gif "resources/public/images/coffee_dance.gif")
```

### 🎯 Next Steps

[ ] Deploy the app to Vercel.

[ ] Add dynamic content to the program detail pages.

[ ] Enhance accessibility and responsiveness.

[ ] Gamify user progress tracking.

## License

Copyright © 2025 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

Made with ❤️ by g0g0's X Roads Team
