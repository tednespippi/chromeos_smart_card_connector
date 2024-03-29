// Copyright 2016 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#ifndef GOOGLE_SMART_CARD_COMMON_CPP_SRC_PUBLIC_UNIQUE_PTR_UTILS_H_
#define GOOGLE_SMART_CARD_COMMON_CPP_SRC_PUBLIC_UNIQUE_PTR_UTILS_H_

#include <memory>
#include <utility>

namespace google_smart_card {

// Replacement of std::make_unique, which will be introduced only in C++14.
template <typename T, typename... Args>
inline std::unique_ptr<T> MakeUnique(Args&&... args) {
  return std::unique_ptr<T>(new T(std::forward<Args>(args)...));
}

}  // namespace google_smart_card

#endif  // GOOGLE_SMART_CARD_COMMON_CPP_SRC_PUBLIC_UNIQUE_PTR_UTILS_H_
